package com.example.cartzyapp.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

// A Realm model class for representing cart items
class CartItem : RealmObject {
    var id: String = ""
    var userId: String = ""
    var productName: String = ""
    var productId: Int = 0
    var price: Double = 0.0
    var quantity: Int = 1
        set(value) {
            // Ensure the quantity is always at least 1
            field = if (value < 1) 1 else value
        }

    // Additional computed property for the total price
    val totalPrice: Double
        get() = price * quantity

    // Function to convert a CartItem to a Map<String, Any> for Firestore
    // In CartItem.kt
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "productName" to productName,
            "productId" to productId,
            "price" to price,
            "quantity" to quantity
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>): CartItem? {
            return try {
                CartItem().apply {
                    id = map["id"] as? String ?: return null
                    productName = map["productName"] as? String ?: ""
                    productId = (map["productId"] as? Number)?.toInt() ?: 0
                    price = (map["price"] as? Number)?.toDouble() ?: 0.0
                    quantity = (map["quantity"] as? Number)?.toInt() ?: 1
                }
            } catch (e: Exception) {
                null // Return null if there's an issue with the map
            }
        }
    }


}
