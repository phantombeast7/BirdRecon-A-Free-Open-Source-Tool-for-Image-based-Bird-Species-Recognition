from flask import Flask, request, jsonify
from googlesearch import search
from bs4 import BeautifulSoup
import requests
import google.generativeai as genai

app = Flask(__name__)


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


# Function to perform Google Image Search for the bird
def get_gemini_info(query):
    # Replace 'YOUR_API_KEY' with your actual Google API key
    genai.configure(api_key="Your_Gemini_API")

    # Initialize a GenerativeModel instance with 'gemini-pro' model
    model = genai.GenerativeModel('gemini-pro')

    # Generate content using Gemini
    response = model.generate_content(
        f"Generate a summary about the {query} bird in 150 words in the language as the name of the bird.and if this is not a bird then dont mention 'bird' in the summary and if it is bird then mention 'bird' in the summary and only accept real birds")

    # Limit the response to approximately 150 words
    limited_response = ' '.join(response.text.split()[:150])

    return {'summary': limited_response}  # Assuming Gemini does not return image URLs


# Define a route for handling bird information
@app.route('/bird_info', methods=['POST'])
def get_bird_info():
    input_bird_name = request.json.get('bird_name').capitalize()

    # Check if the input is a bird name using Gemini
    gemini_info = get_gemini_info(input_bird_name)
    print(gemini_info)

    bird_image_url = get_wikimedia_image(input_bird_name)
    bird_image_url = bird_image_url['image_url']
    print(f"\nBird image url: {bird_image_url}")

    if bird_image_url:
        return jsonify(
            {'bird_name': input_bird_name, 'summary': gemini_info['summary'], 'image_url': bird_image_url}), 200
    else:
        return jsonify({'message': 'No HD image found for the given bird name.'}), 404


if __name__ == '__main__':
    app.run(debug=False, port=8080)  # Run the app on port 8080
