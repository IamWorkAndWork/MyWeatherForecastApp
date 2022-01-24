# MyWeatherForecastApp

WeatherForecast Test Application

# How To Build

  - download the SouceCode and then unzip the file 
  - open with android studio
  - go to open File -> Open -> select source code root folder (MyWeatherForecastApp-master) and then waiting for library downloading and build the project.

- unzip the file
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/1unzipFile.png)

- open with android studio
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/2openAndroidStudioAndOpen.png)
  
- selected the project source code
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/3selectProject.png)

- waiting for the building process
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/4waitingForBuild.png)

- if build is done then press "run" to run the project
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/5runTheProject.png)

- source code structure
![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/6projectStructure)

# Project Structure & Libraries

- MVVM + Clean Architecture
- Modular Architecture   
- Koin Dependency Injection
- Jetpack Room, Navigation Component, DataBinding, DataStore, Paging3
- Retrofit for Networking  
- Airbnb Epoxy : for building complex screens in a RecyclerView
- Coil : Image Loading library

# reusability Implement
- for reuse to support for 7-day forecast
- assume it need to call for another API to get the Data. 
- first go to data module and add the API endpoint with request and response model
- and do implement for getting data from API in repository, dataSource
- in domain layer then create a Usecase class that call for getting data from the repository
- the presentation layer in HomeViewModel. the function "loadHomeUiData" is responsible to getting data from usecase and create a UI model to render.
- with seal class "HomeUiModel". it the place to decide the type of UI layout we want.

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/reuse1.png)

- getting data from 7-day forecast is handle in function "loadHomeUiData" with combine operation so we can merge data from another API and mapping the sequence of the UI here.

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/reuse2.png)

- in the "ResultToHomeUiModelMapper" is where to handle for sequence of layout we want to render in homeFragment

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/reuse3.png)

- in the "HomeUiController" is where to render the UI layout according with the xml layout and binding the data to show

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/reuse4.png)

- with the Epoxy library, we can now flexible to reuse and adjust the layout in UI and this is layout structure in the app.

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/uisection.png)

# Demo

![project](https://github.com/IamWorkAndWork/MyWeatherForecastApp/blob/master/images/appDemo.png)