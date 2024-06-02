# Use the official Python image as base
FROM python:3.10.7

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt \
    && pip install --no-cache-dir tensorflow \
    && pip install --no-cache-dir pillow

# Copy the Flask application code into the container
COPY . .

# Expose the port that Flask runs on
EXPOSE 8080

# Define environment variables
ENV FLASK_APP=main.py \
    FLASK_RUN_HOST=0.0.0.0 \
    FLASK_RUN_PORT=8080 \
    FLASK_ENV=production

# Command to run the Flask application
CMD ["flask", "run"]
