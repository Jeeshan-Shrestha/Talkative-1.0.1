# Talkative 💬

**Talkative** is a real-time chat application built with modern Android technologies. It allows users to sign up, log in, and chat instantly with others over WebSockets.

## Features

* 🔐 User authentication (Login/Signup)
* 🧑 Real-time one-to-one messaging
* 🌐 WebSocket connection with status updates
* 🎨 Beautiful UI built using Jetpack Compose
* 💬 Messages are categorized as Sent or Received
* 📱 Fully responsive and fluid chat experience

## Tech Stack

### Frontend (Android)

* **Kotlin**
* **Jetpack Compose**
* **MVVM Architecture**
* **WebSocket with OkHttp**
* **Hilt for Dependency Injection**
* **Coroutines + Flow** for reactive programming

### Backend

* **Java Spring Boot**
* **MVC Architecture**
* **WebSocket API**
* **MongoDb**

### ScreenShots

 <a href="https://imgur.com/OUCtetW">
    <img src="https://imgur.com/OUCtetW.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/RnpBVGu">
    <img src="https://imgur.com/RnpBVGu.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
    <a href="https://imgur.com/MIQRpuY">
    <img src="https://imgur.com/MIQRpuY.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
   <a href="https://imgur.com/j9zrnCe">
    <img src="https://imgur.com/j9zrnCe.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>

## Getting Started

### Prerequisites

* Android Studio Hedgehog or later
* JDK 17+
* Gradle
* Backend server running locally or deployed

### Clone the repository

```bash
git clone https://github.com/Nara476/talkative.git
cd talkative
```

### Run the App

1. Start your backend Spring Boot server.
2. Open the project in Android Studio.
3. Run the app on an emulator or physical device.

Make sure the WebSocket URL is correctly configured in the repository/service layer of your Android app to point to the correct backend server.

## Folder Structure

```
├── Screens/
│   ├── homeScreen/       # Chat UI and ViewModel
│   ├── addUsername/      # Login screen
│   ├── signupScreen/     # Sign-up screen
├── Components/           # Reusable UI components
├── model/                # Data models like Message, MessageResponse
├── repository/           # Chat and Login Repositories
├── navigation/           # Navigation setup using Jetpack Compose
├── loadingState/         # Enum for loading states (IDLE, LOADING, FAILED, SUCCESS)
```

## Future Improvements

* Group chat support
* Chat history storage in DB
* Firebase Cloud Messaging for notifications
* UI polishing and animations


