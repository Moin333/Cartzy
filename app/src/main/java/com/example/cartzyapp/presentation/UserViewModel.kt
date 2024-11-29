package com.example.cartzyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartzyapp.model.User
import com.example.cartzyapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Login function
    fun login(email: String, password: String) {
        println("Login called with email: $email")
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.loginUser(email, password) { success, errorMessage ->
                if (success) {
                    println("Login successful")
                    fetchUser(email)
                    println("User fetched: ${_user.value}")
                } else {
                    println("Login failed with error: $errorMessage")
                    _errorMessage.value = errorMessage ?: "Login failed. Please check your credentials."
                    _isLoading.value = false
                }
            }
        }
    }

    // Sign-up function
    fun signUp(email: String, password: String, username: String) {
        println("Sign-up called with email: $email")
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.registerUser(email, password) { success, errorMessage ->
                if (success) {
                    println("Registration successful")
                    val newUser = User().apply {
                        id = UUID.randomUUID().toString()
                        this.email = email
                        this.username = username
                    }
                    println("New user created: $newUser")
                    insertUser(newUser)
                    println("User inserted: ${_user.value}")
                } else {
                    println("Sign-up failed with error: $errorMessage")
                    _errorMessage.value = errorMessage ?: "Registration failed. Please try again."
                    _isLoading.value = false
                }
            }
        }
    }

    // Fetch user data
    private fun fetchUser(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                println("Fetching user with email: $email")
                val currentUser = userRepository.getUserByEmail(email)
                _user.value = currentUser
                println("Fetched user: $currentUser")
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
                _errorMessage.value = "Failed to fetch user: ${e.message}"
            }
            _isLoading.value = false
        }
    }

    // Insert user to database
    private fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                println("Inserting user: $user")
                userRepository.insertUser(user)
                println("User inserted successfully")
                _user.value = user

            } catch (e: Exception) {
                println("Error inserting user: ${e.message}")
                _errorMessage.value = "Failed to save user: ${e.message}"
            }
            _isLoading.value = false
        }
    }

    // Show error
    fun showError(message: String) {
        _errorMessage.value = message
    }

    // Reset error message
    fun resetErrorMessage() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000) // Delay before clearing error
            _errorMessage.value = null
        }
    }
}
