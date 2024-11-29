package com.example.cartzyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartzyapp.model.CartItem
import com.example.cartzyapp.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    /**
     * Fetch cart items for the specified user ID.
     */
    fun fetchCartItems(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val items = cartRepository.getCartItems(userId)
                _cartItems.value = items
            } catch (e: Exception) {
                _error.value = "Error loading cart items: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Sync the cart items of a specific user with the cloud.
     */
    fun syncCartWithCloud(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                cartRepository.syncCartWithCloud(userId, _cartItems.value)
            } catch (e: Exception) {
                _error.value = "Error syncing cart with cloud: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Insert or update a cart item for a specific user.
     */
    fun insertCartItem(userId: String, cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cartRepository.insertOrUpdateCartItem(userId, cartItem)
                fetchCartItems(userId) // Refresh the cart items after insertion
                syncCartWithCloud(userId) // Sync immediately after insertion
            } catch (e: Exception) {
                _error.value = "Error adding item to cart: ${e.message}"
            }
        }
    }

    /**
     * Remove a cart item for a specific user.
     */
    fun removeFromCart(userId: String, cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cartRepository.removeCartItem(userId, cartItem)
                fetchCartItems(userId) // Refresh cart items after removal
            } catch (e: Exception) {
                _error.value = "Error removing item from cart: ${e.message}"
            }
        }
    }
}