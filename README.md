# <div align="center"><img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=30&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=🦜+BirdRecon;Bird+Species+Recognition" alt="BirdRecon" /></div>

<div align="center">

<img src="https://img.shields.io/badge/License-MIT-green?style=flat&logo=opensourceinitiative&logoColor=white" alt="License MIT" />
<img src="https://img.shields.io/badge/Gemini_API-Connected-4285F4?style=flat&logo=google&logoColor=white" alt="Gemini API Connected" />
<img src="https://img.shields.io/badge/TensorFlow_Lite-Enabled-FF6F00?style=flat&logo=tensorflow&logoColor=white" alt="TensorFlow Lite Enabled" />
<img src="https://img.shields.io/badge/Deploy-Azure-0078D4?style=flat&logo=microsoftazure&logoColor=white" alt="Deploy Azure" />

<br/>
<br/>


<h2>
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=22&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=Free+Open+Source+Tool;for+Bird+Species+Recognition" alt="Typing SVG" />
</h2>

<p align="center">
  <em>A comprehensive tool for ornithologists and bird enthusiasts to identify and learn about bird species.</em>
</p>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

</div>

## <img src="https://media2.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif?cid=ecf05e47a0n3gi1bfqntqmob8g9aid1oyj2wr3ds3mg700bl&rid=giphy.gif" width="28"> **What is BirdRecon?**

**BirdRecon** is a free open-source bird identification application designed to support ornithologists and bird enthusiasts in their field research and observations. The system integrates Google Gemini for detailed species information and Wikimedia Commons for similar image retrieval.

<div align="center">

### 🎯 **Core Features**

<table>
<tr>
<td align="center">
  <img src="https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExY2ZwcXpueGRhMDk2NDFjdzB3MjNucXYwNHlvZW14ZXd1ZHF5ZnB6OSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/XWTENcavWkhVhobHaa/giphy.gif" width="60px"/><br/><b>Image Recognition</b>
</td>
<td>Advanced bird species recognition using ensemble learning models</td>
</tr>
<tr>
<td align="center">
  <img src="https://media.giphy.com/media/l0HlNaQ6gWfllcjDO/giphy.gif" width="60px"/><br/><b>Detailed Information</b>
</td>
<td>Comprehensive species information powered by Google Gemini API</td>
</tr>
<tr>
<td align="center">
  <img src="https://media.giphy.com/media/3o7TKtnuHOHHUjR38Y/giphy.gif" width="60px"/><br/><b>Similar Images</b>
</td>
<td>Related bird images from Wikimedia Commons</td>
</tr>
<tr>
<td align="center">
  <img src="https://media.giphy.com/media/26tn33aiTi1jkl6H6/giphy.gif" width="60px"/><br/><b>Multi-platform</b>
</td>
<td>Available on both web and mobile platforms</td>
</tr>
</table>

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

</div>

## <img src="https://media.giphy.com/media/iY8CRBdQXODJSCERIr/giphy.gif" width="30"> **Prerequisites**

<table>
<tr>
<td><img src="https://www.docker.com/wp-content/uploads/2022/03/vertical-logo-monochromatic.png" width="20"/></td>
<td>Docker</td>
</tr>
<tr>
<td><img src="https://learn.microsoft.com/en-us/azure/media/index/azure-cli.svg" width="20"/></td>
<td>Azure CLI</td>
</tr>
<tr>
<td><img src="https://azure.microsoft.com/favicon.ico" width="20"/></td>
<td>Microsoft Azure account</td>
</tr>
</table>
## <img src="https://media.giphy.com/media/iY8CRBdQXODJSCERIr/giphy.gif" width="30"> **App Setup**

1. Download and install [Android Studio](https://developer.android.com/studio)
2. Open the project in Android Studio
3. Update the endpoint URLs in `SearchFragment.kt`:
   - Line 201: Update URL for name search
   - Line 258: Update URL for image search

## <img src="https://media.giphy.com/media/dWesBcTLavkZuG35MI/giphy.gif" width="28"> **Website Setup**

1. Navigate to the Website directory
2. Update the endpoint URL in `app.py` (line 26)
3. Follow the Server Deployment steps to deploy
## <img src="https://media.giphy.com/media/dWesBcTLavkZuG35MI/giphy.gif" width="28"> **Server Deployment**

### Steps

1. **Log in to Azure:**
```sh
az login
```

2. **Log in to Azure Container Registry:**
```sh
az acr login --name <YourContainerRegistryName>
```

3. **Build the Docker image:**
```sh
docker build -t <YourLocalImageName>:<Tag> .
```

4. **Tag the Docker image:**
```sh
docker tag <YourLocalImageName>:<Tag> <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
```

5. **Push the Docker image:**
```sh
docker push <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
```

6. **Create an Azure App Service plan:**
```sh
az appservice plan create --name <YourAppServicePlanName> --resource-group <YourResourceGroupName> --sku B1 --is-linux
```

7. **Create a web app:**
```sh
az webapp create --resource-group <YourResourceGroupName> --plan <YourAppServicePlanName> --name <YourWebAppName> --deployment-container-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag>
```

8. **Configure the web app:**
```sh
az webapp config container set --resource-group <YourResourceGroupName> --name <YourWebAppName> --docker-custom-image-name <YourContainerRegistryName>.azurecr.io/<YourImageName>:<Tag> --docker-registry-server-url https://<YourContainerRegistryName>.azurecr.io
```


<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

## 🤝 **Contributing**

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">

<div align="center">

### 🎯 **Ready to start identifying birds?**

<a href="https://birdrecon.azurewebsites.net/">
  <img src="https://img.shields.io/badge/Get_Started-0078D4?style=flat&logo=microsoftazure&logoColor=white&labelColor=black" alt="Get Started"/>
</a>

<a href="https://github.com/phantombeast7/BirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Species-Recognition/issues">
  <img src="https://img.shields.io/badge/Report_Bug-FF0000?style=flat&logo=github&logoColor=white&labelColor=black" alt="Report Bug"/>
</a>

<a href="https://github.com/phantombeast7/BirdRecon-A-Free-Open-Source-Tool-for-Image-based-Bird-Species-Recognition/issues">
  <img src="https://img.shields.io/badge/Request_Feature-4CAF50?style=flat&logo=github&logoColor=white&labelColor=black" alt="Request Feature"/>
</a>

<br/>
<br/>

<a href="https://github.com/phantombeast7">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=12&duration=3000&pause=1000&color=00C7B7&center=true&vCenter=true&width=435&lines=Made+with+❤️+by+phantombeast7" alt="Made by" />
</a>

</div>
