package com.example.cartzyapp.repository

import com.example.cartzyapp.model.Product
import com.example.cartzyapp.network.ProductApiService
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiService: ProductApiService
) {

    // Function to fetch products from the network
    suspend fun getProducts(): List<Product> {
        return try {
            val response: Response<List<Product>> = productApiService.getProducts()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                // Handle non-200 responses, like showing an error message to the user
                emptyList()
            }
        } catch (e: Exception) {
            // Handle network errors or unexpected exceptions
            emptyList()
        }
    }
}
