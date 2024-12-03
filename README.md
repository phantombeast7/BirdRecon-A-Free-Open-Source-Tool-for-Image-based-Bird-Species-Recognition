# <div align="center"><img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=30&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=🦜+BirdRecon;Bird+Species+Recognition" alt="BirdRecon" /></div>

<div align="center">

<img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge&labelColor=black" alt="License" />
<img src="https://img.shields.io/badge/Azure-Deployed-0078D4?style=for-the-badge&logo=microsoftazure&labelColor=black" alt="Azure" />
<img src="https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&labelColor=black" alt="Docker" />
<img src="https://img.shields.io/badge/Status-Active-4CAF50?style=for-the-badge&labelColor=black" alt="Status" />

<br/>
<br/>

<img src="https://raw.githubusercontent.com/yourusername/BirdRecon/main/assets/logo.png" alt="BirdRecon Logo" width="200" height="200" style="border-radius: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);"/>

<h2>
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=22&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=Free+Open+Source+Tool;for+Bird+Species+Recognition" alt="Typing SVG" />
</h2>

<p align="center">
  <em>A comprehensive tool for ornithologists and bird enthusiasts powered by Google Gemini and Wikimedia Commons</em>
</p>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

</div>

## 🌟 **Introduction**

BirdRecon is a cutting-edge, free open-source bird identification application designed to support ornithologists and bird enthusiasts. By integrating Google Gemini for detailed species information and Wikimedia Commons for similar image retrieval, we provide a comprehensive reference tool that bridges the gap between amateur bird watching and professional research.

### 🎯 **Key Features**

<div align="center">

<table>
<tr>
<td align="center"><img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDd6Y3E2Y2ptdnhxNXJ1NHYyeHB0Ym8zY2t1bWQyOXJtOWRtY2JxdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/3o7TKSjRrfIPjeiVyE/giphy.gif" width="60px"/><br/><b>Image Recognition</b></td>
<td>Advanced bird species identification from uploaded images</td>
</tr>
<tr>
<td align="center"><img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDd6Y3E2Y2ptdnhxNXJ1NHYyeHB0Ym8zY2t1bWQyOXJtOWRtY2JxdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/3oKIPEqDGUULpEU0aQ/giphy.gif" width="60px"/><br/><b>Name Search</b></td>
<td>Search by bird species names with detailed information</td>
</tr>
<tr>
<td align="center"><img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDd6Y3E2Y2ptdnhxNXJ1NHYyeHB0Ym8zY2t1bWQyOXJtOWRtY2JxdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/xT9IgzoKnwFNmISR8I/giphy.gif" width="60px"/><br/><b>Multi-platform</b></td>
<td>Available on both mobile and web platforms</td>
</tr>
<tr>
<td align="center"><img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDd6Y3E2Y2ptdnhxNXJ1NHYyeHB0Ym8zY2t1bWQyOXJtOWRtY2JxdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/l0HlQXlQ3nHyLMvte/giphy.gif" width="60px"/><br/><b>Open Source</b></td>
<td>Community-driven development and customization</td>
</tr>
</table>

</div>

## 📋 **Prerequisites**

<table>
<tr>
<td><img src="https://www.docker.com/wp-content/uploads/2023/04/cropped-Docker-favicon-32x32.png" width="20"/></td>
<td><a href="https://docs.docker.com/get-docker/">Docker</a></td>
</tr>
<tr>
<td><img src="https://learn.microsoft.com/en-us/azure/media/index/azure-cli.svg" width="20"/></td>
<td><a href="https://learn.microsoft.com/en-us/cli/azure/install-azure-cli-windows?tabs=azure-cli">Azure CLI</a></td>
</tr>
<tr>
<td><img src="https://azure.microsoft.com/favicon.ico" width="20"/></td>
<td><a href="https://azure.microsoft.com/en-us/free/">Microsoft Azure Account</a></td>
</tr>
</table>

## 🚀 **Server Deployment**

### **Important Note**
Before starting, download the model file from the provided drive link in `ensemble_final.tflite Link` and place it in:
```
BirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Identification/Server File/Search By Image/
```

### **Deployment Steps**

<details>
<summary>1️⃣ Azure Setup</summary>

```bash
# Login to Azure
az login

# Login to Azure Container Registry
az acr login --name <YourContainerRegistryName>
```
</details>

<details>
<summary>2️⃣ Docker Configuration</summary>

```bash
# Build Docker image
docker build -t <YourLocalImageName>:<Tag> .

# Tag Docker image
docker tag <YourLocalImageName>:<Tag> <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>

# Push to Azure Container Registry
docker push <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
```
</details>

<details>
<summary>3️⃣ Azure App Service Setup</summary>

```bash
# Create App Service plan
az appservice plan create --name <YourAppServicePlanName> --resource-group <YourResourceGroupName> --sku B1 --is-linux

# Create web app
az webapp create --resource-group <YourResourceGroupName> --plan <YourAppServicePlanName> --name <YourWebAppName> --deployment-container-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>

# Configure container registry
az webapp config container set --resource-group <YourResourceGroupName> --name <YourWebAppName> --docker-custom-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag> --docker-registry-server-url https://<YourContainerRegistryName>.azurecr.io
```
</details>

## 📱 **App Setup**

1. Install [Android Studio](https://developer.android.com/studio)
2. Open the project in Android Studio
3. Update API endpoints in `birds/species/birdsspeciesclassification/ui/search/SearchFragment.kt`:
   - Line 201: Update "search by name" endpoint
   - Line 258: Update "search by image" endpoint

## 🌐 **Website Setup**

1. Navigate to `Website/app.py`
2. Update the endpoint URL on line 26 with your "search by image upload" endpoint
3. Follow the Server Deployment steps to deploy the website

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 🤝 **Contributing**

<img src="https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png">

We welcome contributions! Here's how you can help:

1. 🍴 Fork the repository
2. 🌿 Create your feature branch
3. ✍️ Commit your changes
4. 🚀 Push to the branch
5. 🎉 Open a Pull Request

<img src="https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png">

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

<div align="center">

### 🎯 **Ready to start identifying birds?**

<a href="https://github.com/yourusername/BirdRecon">
  <img src="https://img.shields.io/badge/Get_Started-00C7B7?style=for-the-badge&logo=github&logoColor=white&labelColor=black" alt="Get Started"/>
</a>
<a href="https://github.com/yourusername/BirdRecon/issues">
  <img src="https://img.shields.io/badge/Report_Bug-FF0000?style=for-the-badge&logo=github&logoColor=white&labelColor=black" alt="Report Bug"/>
</a>
<a href="https://github.com/yourusername/BirdRecon/issues">
  <img src="https://img.shields.io/badge/Request_Feature-4CAF50?style=for-the-badge&logo=github&logoColor=white&labelColor=black" alt="Request Feature"/>
</a>

<br/>
<br/>

<a href="https://github.com/yourusername">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=12&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=Made+with+❤️+by+Your+Name" alt="Made by" />
</a>

<img src="https://raw.githubusercontent.com/trinib/trinib/main/.images/footer.svg" width="100%">

</div>
