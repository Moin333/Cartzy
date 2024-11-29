package com.example.cartzyapp.repository

import android.util.Log
import com.example.cartzyapp.model.CartItem
import com.example.cartzyapp.network.FirestoreService
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val realm: Realm,
    private val firestoreService: FirestoreService
) {

    /**
     * Insert or update a cart item in the local Realm database.
     * If the item exists, it's updated; otherwise, it's inserted.
     */
    suspend fun insertOrUpdateCartItem(userId: String, cartItem: CartItem) = withContext(Dispatchers.IO) {
        try {
            realm.write {
                // Check if the item with the same ID and userId already exists
                val existingItem: CartItem? = query<CartItem>("id == $0 AND userId == $1", cartItem.id, userId).first().find()

                if (existingItem != null) {
                    // Update the existing item
                    findLatest(existingItem)?.apply {
                        productName = cartItem.productName
                        productId = cartItem.productId
                        price = cartItem.price
                        quantity = cartItem.quantity
                    }
                } else {
                    // Insert a new item if it doesn't exist
                    cartItem.userId = userId // Ensure userId is set
                    copyToRealm(cartItem)
                }
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "Error inserting/updating cart item: ${e.message}")
            throw e
        }
    }

    /**
     * Fetch all cart items for a specific user from the local Realm database.
     */
    suspend fun getCartItems(userId: String): List<CartItem> = withContext(Dispatchers.IO) {
        try {
            realm.query<CartItem>("userId == $0", userId).find()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error fetching cart items for user: ${e.message}")
            emptyList()
        }
    }

    /**
     * Sync local cart items with Firebase Firestore.
     */
    suspend fun syncCartWithCloud(userId: String, cartItems: List<CartItem>) = withContext(Dispatchers.IO) {
        try {
            firestoreService.saveCartItems(userId, cartItems) // Save items under the user's document
        } catch (e: Exception) {
            Log.e("CartRepository", "Error syncing cart items with Firestore: ${e.message}")
            throw e
        }
    }

    /**
     * Fetch all cart items for a user from Firebase Firestore.
     */
    suspend fun fetchCartItemsFromCloud(userId: String): List<CartItem> = withContext(Dispatchers.IO) {
        try {
            firestoreService.getCartItems(userId)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error fetching cart items from Firestore: ${e.message}")
            emptyList()
        }
    }

    /**
     * Observe cart items from the local Realm database for real-time updates.
     */
    fun observeCartItems(userId: String): Flow<List<CartItem>> {
        return realm.query<CartItem>("userId == $0", userId).asFlow().map { changes: ResultsChange<CartItem> ->
            changes.list // Return the current list of cart items whenever there's a change
        }
    }

    /**
     * Remove a specific cart item from the local Realm database.
     */
    suspend fun removeCartItem(userId: String, cartItem: CartItem) = withContext(Dispatchers.IO) {
        try {
            realm.write {
                val itemToDelete = query<CartItem>(
                    "id == $0 AND userId == $1",
                    cartItem.id,
                    userId
                ).first().find()
                itemToDelete?.let { delete(it) }
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "Error removing cart item: ${e.message}")
            throw e
        }
    }
}
