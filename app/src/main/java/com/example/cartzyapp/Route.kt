package com.example.cartzyapp

sealed class Route(val route: String) {
    object Login : Route("login")
    object Home : Route("home/{userId}") {
        fun createRoute(userId: String) = "home/$userId"
    }
    object Cart : Route("cart/{userId}") {
        fun createRoute(userId: String) = "cart/$userId"
    }
}