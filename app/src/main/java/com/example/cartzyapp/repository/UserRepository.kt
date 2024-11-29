package com.example.cartzyapp.repository

import com.example.cartzyapp.model.User
import com.example.cartzyapp.network.AuthService
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor
    (
    private val realm: Realm,
    private val authService: AuthService
            )
{

    // Function to insert a user into Realm database with error handling
    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        try {
            realm.write {
                copyToRealm(user)
            }
        } catch (e: Exception) {
            // Handle Realm-specific errors, such as logging or notifying the user
        }
    }

    // Function to register a user using Firebase Auth service with error handling
    fun registerUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {
            authService.signUp(email, password) { isSuccess ->
                onResult(isSuccess, if (isSuccess) null else "Registration failed")
            }
        } catch (e: Exception) {
            // Handle potential errors during Firebase registration
            onResult(false, e.message)
        }
    }

    // Function to login a user using Firebase Auth service with error handling
    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {
            authService.login(email, password) { isSuccess ->
                onResult(isSuccess, if (isSuccess) null else "Login failed")
            }
        } catch (e: Exception) {
            // Handle potential errors during Firebase login
            onResult(false, e.message)
        }
    }

    // Function to get a user by email from Realm database with error handling
    suspend fun getUserByEmail(email: String): User? = withContext(Dispatchers.IO) {

        try {

            realm.query<User>("email == $0", email).first().find()
        } catch (e: Exception) {
            // Handle errors when querying Realm database
            null
        }
    }
}
