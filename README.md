# BirdRecon: A Free Open Source Tool for Image based Bird Species Recognition

## Introduction

This repository  presents a free open-source bird identification application designed to support ornithologists and bird enthusiasts in their field research and observations with user-friendly features.

The system architecture integrates Google Gemini for detailed species information and Wikimedia Commons for similar image retrieval, offering a comprehensive reference tool. Users can initiate searches using bird names or uploaded images, with the system optimized for efficient processing to ensure a seamless user experience.

A key feature of this project is its open-source nature, with the entire codebase publicly available on GitHub. This approach fosters community collaboration, allowing continuous improvement and customization to meet specific research needs. The application is accessible through both mobile and web platforms, maximizing its reach and utility.

This research contributes to the field of ornithology by providing a user-friendly tool that bridges the gap between amateur bird watching and professional research. The multilingual support and open-source framework make it particularly valuable for field surveys across diverse regions, potentially accelerating data collection and analysis in ornithological studies.


This project involves deploying a server for bird species classification using ensemble learning, which includes two functionalities: search by bird name and search by image upload at the location BirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Identification/Server File/. The following steps guide you through deploying the server on Microsoft Azure, setting up the Android app, and configuring the website.Before starting after you download all the files go to this directory 
"BreadcrumbsBirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Identification/Server File/Search By Image" open "ensemble_final.tflite Link"  text file and download the model from the drive as the model is a bit large in size so we have kept it in drive place the downloaded file in "BreadcrumbsBirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Identification/Server File/Search By Image" location .Next Follow the below steps.

## Prerequisites


1. Download and Install [Docker](https://docs.docker.com/get-docker/)
2. Download and Install [Azure CLI](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli-windows?tabs=azure-cli)
3. Create a account on [Microsoft Azure account](https://azure.microsoft.com/en-us/free/)


## Server Deployment

### Steps

1. **Log in to Azure:**
    ```sh
    az login
    ```

2. **Log in to Azure Container Registry:**
    ```sh
    az acr login --name <YourContainerRegistryName>
    ```
    Replace `<YourContainerRegistryName>` with the name of your Azure Container Registry (e.g., `birdsdetection`).

3. **Build the Docker image:**
    ```sh
    docker build -t <YourLocalImageName>:<Tag> .
    ```
    Navigate to the directory containing your Dockerfile and run the above command. Replace `<YourLocalImageName>` with a name for your local Docker image (e.g., `my_flask_app`) and `<Tag>` with a tag for the version (e.g., `v1`).

4. **Tag the Docker image:**
    ```sh
    docker tag <YourLocalImageName>:<Tag> <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
    ```
    Replace `<YourLocalImageName>` and `<Tag>` with the names used in the previous step. Replace `<YourContainerRegistryName>` with the name of your Azure Container Registry, and `<YourImageName>` with the desired name for the image in the registry (e.g., `my_flask_app`).

5. **Push the Docker image to Azure Container Registry:**
    ```sh
    docker push <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
    ```
    Replace `<YourContainerRegistryName>`, `<YourImageName>`, and `<Tag>` with the names used in the previous steps.

6. **Create an Azure App Service plan:**
    ```sh
    az appservice plan create --name <YourAppServicePlanName> --resource-group <YourResourceGroupName> --sku B1 --is-linux
    ```
    Replace `<YourAppServicePlanName>` with a name for your App Service plan, and `<YourResourceGroupName>` with the name of your resource group (e.g., `BirdsWebsite`).

7. **Create a web app with Docker container:**
    ```sh
    az webapp create --resource-group <YourResourceGroupName> --plan <YourAppServicePlanName> --name <YourWebAppName> --deployment-container-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
    ```
    Replace `<YourResourceGroupName>`, `<YourAppServicePlanName>`, `<YourWebAppName>`, `<YourContainerRegistryName>`, `<YourImageName>`, and `<Tag>` with the names used in the previous steps. `<YourWebAppName>` should be a unique name for your web app.

8. **Configure the web app to use the container registry:**
    ```sh
    az webapp config container set --resource-group <YourResourceGroupName> --name <YourWebAppName> --docker-custom-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag> --docker-registry-server-url https://<YourContainerRegistryName>.azurecr.io
    ```
    Replace `<YourResourceGroupName>`, `<YourWebAppName>`, `<YourContainerRegistryName>`, `<YourImageName>`, and `<Tag>` with the names used in the previous steps.

After deploying both functionalities, you will receive two URL endpoints, which are needed for the next steps.

## App Setup

1. Download and install [Android Studio](https://developer.android.com/studio).
2. Open Android Studio, click on "Open," navigate to the folder downloaded from GitHub, and open it.
3. Change the endpoint URLs in the code:
    - Go to `birds/species/birdsspeciesclassification/ui/search/SearchFragment.kt`.
    - Change the URL at line 201 to the endpoint for "search by name of the bird."
    - Change the URL at line 258 to the endpoint for "search by image of the bird."

Your app is now ready.

## Website Setup

Before deploying the website, update the endpoint URL in the code:

1. Navigate to `BirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Identification/Website/` and open `app.py` in a text editor.
2. Replace the URL on line 26 with the endpoint for "search by image upload."

Follow the Server Deployment steps to deploy the updated website.

## Conclusion

After completing all the steps, your app, website, and server will be ready for use from anywhere.

