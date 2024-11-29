package com.example.cartzyapp.network

import android.util.Log
import com.example.cartzyapp.model.CartItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore // Marking as `private` since it's not exposed directly
) {

    suspend fun saveCartItems(userId: String, cartItems: List<CartItem>) {
        try {
            val cartRef = firestore.collection("users").document(userId).collection("cart")
            val batch = firestore.batch()

            cartItems.forEach { cartItem ->
                val cartItemRef = cartRef.document(cartItem.id)
                batch.set(cartItemRef, cartItem.toMap())
            }

            batch.commit().await()  // Committing all writes in a batch
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error saving cart items: ${e.message}")
        }
    }

    suspend fun getCartItems(userId: String): List<CartItem> {
        return try {
            val cartRef = firestore.collection("users").document(userId).collection("cart")
            val snapshot = cartRef.get().await()
            snapshot.documents.mapNotNull { document ->
                document.data?.let { CartItem.fromMap(it) }
            }
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error fetching cart items: ${e.message}")
            emptyList()
        }
    }
}
