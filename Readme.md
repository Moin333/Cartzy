# 🛒CartzyApp
CartzyApp is a e-commerce application built with **Kotlin**, **Jetpack Compose** and **Hilt** for dependency injection. It supports user authentication, product browsing, cart management, and navigation using Jetpack Compose's powerful tools. The app emphasizes clean UI/UX design, modular architecture, and scalability.

---

## Table of Contents
- [Known Issues](⚠️ Known Issues)
- [Features](🚀 Features)
- [Architecture](🧱 Architecture)
- [Screens](📱 Screens)
- [Tech Stack](🛠️ Tech Stack)
- [Screenshots](📸 Screenshots)
- [Installation](🧰 How to Run the Project)
- [Contribution](🤝 Contributing)
- [License](📄 License)
- [Feedback](💬 Feedback)

---

## ⚠️ Known Issues
1. ❌ Cart Screen Remove Button Bug:
The Remove button in CartScreen.kt does not function as expected.
2. 💡 Database Sync Issue:
Avoid using a local database like Realm for this app as it causes problems with synchronization and increases time complexity.
Note: Realm was used in this project for learning purposes only.

---

## 🚀 Features
- **User Authentication:** Login and Sign-up functionality with error handling.
- **Product Listing:** Browse products fetched from the backend.
- **Cart Management:** Add items to the cart and manage cart contents.
- **Logout Functionality:** Securely log out and navigate back to the login screen.
- **Dynamic Navigation:** Navigate between screens with contextual data.
- **Responsive UI:** Built using Jetpack Compose for dynamic layouts.

---

## 🧱 Architecture
The app follows the **MVVM (Model-View-ViewModel)** architecture:
- **ViewModel:** Manages UI-related data and communicates with repositories.
- **Repository:** Handles business logic and data fetching.
- **Compose UI:** Creates a reactive UI that updates based on state changes.

---

## 📱 Screens
### 1. **Login Screen**
- Allows users to log in or sign up.
- Validates input and displays error messages when fields are incomplete.
- Navigates to the Home Screen on successful login/sign-up.

### 2. **Home Screen**
- Displays a list of products fetched from the backend.
- Allows users to add products to the cart.
- Shows the number of cart items.

### 3. **Cart Screen**
- Displays all items added to the cart.
- Allows users to remove items from the cart(Currently Buggy).
- Fetches cart details specific to the logged-in user.

---

## 🛠️ Tech Stack
- **Kotlin**
- **Jetpack Compose:** For declarative UI components.
- **Hilt:** Dependency injection framework.
- **State Management:** Utilizes `State` and `collectAsState`.
- **Navigation Component:** Handles screen transitions.
- **Realm Database:** For local storage.
- **Firebase:** For cloud sync.


---

## 📸 Screenshots

### 🌙 Dark Mode
<p align="center"> <img src="./Screenshot 0 Dark.jpg" alt="Dark Mode Screenshot 1" width="180" /> <img src="./Screenshot 1 Dark.jpg" alt="Dark Mode Screenshot 2" width="180" /> <img src="./Screenshot 2 Dark.jpg" alt="Dark Mode Screenshot 3" width="180" /></p>

### ☀️ Light Mode
<p align="center"> <img src="./Screenshot 0 Light.jpg" alt="Light Mode Screenshot 1" width="180" /> <img src="./Screenshot 1 Light.jpg" alt="Light Mode Screenshot 2" width="180" /> <img src="./Screenshot 2 Light.jpg" alt="Light Mode Screenshot 3" width="180" /> </p>

---

## 🧰 How to Run the Project
1. Clone the Repository 
git clone https://github.com/Moin333/Cartzy.git 
cd note-app-with-realm
2. Open the Project in Android Studio
Make sure you have the latest version of Android Studio installed.
3. Build and Run the App
Connect an Android device or emulator and click Run ▶️ in Android Studio.

---

## 🤝 Contributing
Feel free to fork this repository and submit pull requests to improve the project! 🎉

---

## 📄 License
This project is licensed under the MIT License.

---

## 💬 Feedback
If you have any feedback or issues, please open an issue or reach out. 😊
