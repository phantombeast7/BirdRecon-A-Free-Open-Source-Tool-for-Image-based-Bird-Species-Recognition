from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
import pandas as pd
import requests
import google.generativeai as genai
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from io import BytesIO
import base64
from PIL import Image


app = Flask(__name__)


# Load class indices from the CSV file
def load_class_indices(csv_path):
    class_df = pd.read_csv(csv_path)
    class_indices = {str(row['Class']): row['Index'] for _, row in class_df.iterrows()}
    return class_indices


# Load the TensorFlow Lite model
tflite_model_path = 'ensemble_final.tflite'
interpreter = tf.lite.Interpreter(model_path=tflite_model_path)
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()


# Function to predict the name of the bird given an image using TensorFlow Lite model
def predict_bird_name_lite(image_path, interpreter, class_indices, image_size=(224, 224)):
    # Load and preprocess the image
    img = load_img(image_path, target_size=image_size)
    img_array = img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array = img_array.astype('float32') / 255.0  # Rescale the image

    # Set the input tensor
    interpreter.set_tensor(input_details[0]['index'], img_array)
    # Perform the inference
    interpreter.invoke()
    # Get the output tensor
    output_data = interpreter.get_tensor(output_details[0]['index'])
    # Map the predicted label index to the corresponding bird name
    predicted_class = np.argmax(output_data)
    bird_name = class_indices.get(str(predicted_class), 'Unknown')

    return bird_name


# Load class indices
class_indices = load_class_indices('class_indices.csv')

def get_wikimedia_image(query):
    try:
        # Base URL for Wikimedia Commons API
        base_url = "https://commons.wikimedia.org/w/api.php"

        # Parameters for the API request
        params = {
            "action": "query",
            "format": "json",
            "prop": "imageinfo",
            "generator": "search",
            "gsrsearch": f"{query} bird",
            "gsrnamespace": "6",  # Namespace for images in Wikimedia Commons
            "gsrlimit": "1",  # Limiting to one result
            "iiurlwidth": "200",  # Image width
            "iiurlheight": "150",  # Image height
            "iiprop": "url",  # Properties to include in the image info
        }

        # Make the API request
        response = requests.get(base_url, params=params)
        data = response.json()

        # Check if there are any results
        if "query" in data and "pages" in data["query"]:
            pages = data["query"]["pages"]
            if pages:
                page_id = next(iter(pages))
                image_info = pages[page_id]["imageinfo"][0]
                image_url = image_info["url"]
                return {'image_url': image_url}

        return {'image_url': None}
    except Exception as e:
        print(f"Error getting Wikimedia image: {e}")
        return {'image_url': None}


# Function to get information using Gemini
def get_gemini_info(query):
    # Replace 'YOUR_API_KEY' with your actual Google API key
    GOOGLE_API_KEY = "AIzaSyCDB2nPeHI_Csv9lmsfSH6UK91l-jVXQMc"
    genai.configure(api_key=GOOGLE_API_KEY)

    # Initialize a GenerativeModel instance with 'gemini-pro' model
    model = genai.GenerativeModel('gemini-pro')

    # Generate content using Gemini
    response = model.generate_content(f"Generate a summary about the {query} bird in 150 words.and if this is not a bird then dont mention 'bird' in the summary and if it is bird then mention 'bird' in the summary and only accept real birds")

    # Limit the response to approximately 150 words
    limited_response = ' '.join(response.text.split()[:150])

    return {'summary': limited_response, 'image_url': None}






@app.route('/upload', methods=['POST'])
def upload():
    try:
        # Check if an image was uploaded
        if 'image' not in request.json:
            return jsonify({'error': 'No image data provided.'}), 400

            # Get the Base64-encoded image data from the request
        encoded_image = request.json['image']

        # Decode the Base64-encoded image data into bytes
        image_bytes = base64.b64decode(encoded_image)

        # Convert the image bytes into a PIL Image object
        image = Image.open(BytesIO(image_bytes))

        # Predict the bird species
        predicted_species = predict_bird_name_lite(io.BytesIO(image_bytes), interpreter, class_indices)
        print(f"Predicted Bird Species: {predicted_species}")

        # Fetch information using Gemini
        gemini_info = get_gemini_info(predicted_species)
        print(f"\nBird Info: {gemini_info['summary']}")

        # Perform Wikimedia image search for the bird
        bird_image_url = get_wikimedia_image(predicted_species)
        image_url = bird_image_url['image_url']
        print(f"\nBird image url: {image_url}")

        # Prepare the response
        response_data = {
            'predicted_species': predicted_species,
            'summary': gemini_info['summary'],
            'image_url': image_url
        }

        return jsonify(response_data), 200

    except Exception as e:
        print(f"Error processing the image: {e}")
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=False)