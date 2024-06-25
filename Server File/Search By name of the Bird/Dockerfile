FROM python:3.9-slim
WORKDIR /app

# Copy requirements.txt if your app has dependencies
COPY requirements.txt .
RUN pip install -r requirements.txt

# Copy your application code
COPY . .

EXPOSE 8080  # Expose port 8080 for Flask app


# Command to run the Flask app
CMD ["python", "app.py"]
