# Asclepius 🏥

Asclepius is an Android application designed to help detect skin cancer using machine learning. The app integrates a powerful **TensorFlowLite** model to analyze skin images and identify potential cancer risks. With a clean UI and seamless user experience, Asclepius empowers users to gain insights into their skin health.

## Features 🚀
- Upload an image of the skin.
- Analyze the image for signs of skin cancer using **TensorFlowLite** ML model.
- View detailed results with confidence scores.
- Save and manage previous scans using **Room** database.
- Easily crop images using **uCrop**.
- Displays the latest news about cancer.

## Tech Stack 🛠️
This project is built using the following technologies:
- **Kotlin** - For Android development.
- **Android View** - Standard UI components for interaction.
- **Glide** - For image loading and caching.
- **Lottie** - For adding animations.
- **Retrofit** - For HTTP networking.
- **Room** - For local database management.
- **LiveData** - To observe data changes in a lifecycle-aware manner.
- **Coroutines** - For managing background threads and tasks.
- **Koin (DI)** - Lightweight dependency injection framework.
- **MVVM Architecture** - Scalable and maintainable app structure.
- **TensorFlowLite** - For skin cancer detection using machine learning.
- **uCrop** - For advanced image cropping.

## Installation & Setup ⚙️
1. Clone the repository:
   ```bash
   git clone https://github.com/KristianEka/Asclepius.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on an Android device or emulator.
4. Ensure that the TensorFlowLite model is downloaded and placed in the correct directory before running the app.

## Preview 📸

| Scan Screen | Analysis Results |
|-------------|------------------|
| ![Asclepius-1](https://github.com/user-attachments/assets/fd3a4a9c-7022-4c63-ba51-d18cadc12175) | ![Asclepius-2](https://github.com/user-attachments/assets/1e58daf8-be95-44b1-bfc4-74aa7b4998d5) |
