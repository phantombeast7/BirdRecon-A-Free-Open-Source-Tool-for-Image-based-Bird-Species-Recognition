from flask import Flask, render_template, request, redirect, url_for, jsonify

import requests


app = Flask(__name__)



@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')

@app.route('/upload', methods=['POST'])
def upload():
    try:
        # Check if an image was uploaded
        if 'image' not in request.files:
            return jsonify({'error': 'No image file provided.'}), 400

        # Get the image file from the request
        image_file = request.files['image']

        # Send the image file to the specified URL
        files = {'image': image_file}
        response = requests.post('https://birdsapiv3.azurewebsites.net/upload', files=files)

        # Check if the request was successful
        if response.status_code != 200:
            return jsonify({'error': 'Failed to process the image.'}), 500

        # Extract the response data
        data = response.json()

        # Get the predicted bird species from the response data
        predicted_species = data.get('predicted_species', 'Unknown')
        print(f"Predicted Bird Species: {predicted_species}")

        # Pass the data to the results page
        return redirect(url_for('results', predicted_species=predicted_species, summary=data['summary'], image_url=data['image_url']))

    except Exception as e:
        print(f"Error processing the image: {e}")
        return jsonify({'error': str(e)}), 500


@app.route('/teams', methods=['GET'])
def show_team():
    return render_template('teams.html')


@app.route('/results', methods=['GET'])
def results():
    predicted_species = request.args.get('predicted_species')
    summary = request.args.get('summary')
    image_url = request.args.get('image_url')

    return render_template('results.html', predicted_species=predicted_species, summary=summary, image_url=image_url)


if __name__ == '__main__':
    app.run(debug=False)
