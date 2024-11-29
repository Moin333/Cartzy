package com.example.cartzyapp.network

import com.example.cartzyapp.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}
