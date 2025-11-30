# **Talkative — Social Media Application (Android + Java Backend)**

Talkative is a full-stack social media application similar to Instagram, featuring user profiles, posts, an activity feed, and real-time chat.
The project consists of an **Android application** built using modern Jetpack components and a **Java Spring Boot backend** using JWT authentication with cookies and MongoDB.

---

## **Overview**

The system is composed of:

### **Android (Frontend)**

* Kotlin
* Jetpack Compose UI
* MVVM Architecture
* Hilt Dependency Injection
* Retrofit + OkHttp
* OkHttp WebSocket for real-time chat
* Room Database (local caching)
* SQL queries for local persistence
* Cookie-based authentication via cookie Jar(JWT received from backend)
* Coil for image loading
* Navigation Component (Two Graphs: Auth + Main)
* Multipart uploads for profile images and posts

### **Backend (Java)**

* Spring Boot
* Spring MVC
* Spring Security (JWT Authentication)
* JWT sent as HTTP-only cookie
* MongoDB as primary database
* Spring WebSocket for real-time messaging
* Multipart file handling for images
* RESTful API structure
* Docker 

---

## **Key Features**

### **User Authentication**

* Secure login using JWT (stored in HTTP-only cookie)
* Register new account
* Automatic session management on the Android side using CookieJar
* Cookie persistence across app restarts

### **Profile Management**

* Edit profile: avatar, cover photo, bio, display name
* Multipart upload with optional fields
* View other users' profiles

### **Social Feed**

* Create posts with image and caption
* View feed
* Like and comment system
* Local caching of user details using Room Database

### **Real-Time Chat**

* OkHttp WebSocket client in Android
* Spring WebSocket backend
* Real-time messaging between users

---


# 📸 **Screenshots**

Below are some UI previews of the **Talkative Android App**.

---
<table> <tr> <td><img src="https://i.imgur.com/a2EPji1.png" width="250"></td> <td><img src="https://i.imgur.com/XP5Wdsy.png" width="250"></td> <td><img src="https://i.imgur.com/e0OTdOo.png" width="250"></td> </tr> <tr> <td><img src="https://i.imgur.com/FhpW2mt.png" width="250"></td> <td><img src="https://i.imgur.com/mSXOHQP.png" width="250"></td> <td><img src="https://i.imgur.com/NDZMw5F.png" width="250"></td> </tr> <tr> <td><img src="https://i.imgur.com/zwews9W.png" width="250"></td> <td><img src="https://i.imgur.com/cAPg1sN.png" width="250"></td> <td><img src="https://i.imgur.com/9afKItp.png" width="250"></td> </tr> <tr> <td><img src="https://i.imgur.com/UdG9q9g.png" width="250"></td> <td><img src="https://i.imgur.com/oY1c6Rz.png" width="250"></td> <td><img src="https://i.imgur.com/cYNRk7J.png" width="250"></td> </tr> <tr> <td><img src="https://i.imgur.com/CIKpFUO.png" width="250"></td> <td><img src="https://i.imgur.com/zzDmnED.png" width="250"></td> <td><img src="https://i.imgur.com/pJHNEVO.png" width="250"></td> </tr> <tr> <td><img src="https://i.imgur.com/H9MUMu1.png" width="250"></td> <td><img src="https://i.imgur.com/78Vdq6P.png" width="250"></td> <td></td> </tr> </table>



