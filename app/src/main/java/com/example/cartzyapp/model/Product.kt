package com.example.cartzyapp.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) {
    init {
        require(price >= 0) { "Price cannot be negative" }
        require(title.isNotBlank()) { "Product title cannot be empty" }
    }
}
