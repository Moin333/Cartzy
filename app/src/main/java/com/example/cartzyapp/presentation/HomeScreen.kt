package com.example.cartzyapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cartzyapp.model.CartItem


@Composable
fun HomeScreen(
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    onCartClick: () -> Unit,
    currentUserId: String
) {
    LaunchedEffect(currentUserId) {
        productViewModel.fetchProducts()
        cartViewModel.fetchCartItems(currentUserId)
    }

    val products = productViewModel.productList.collectAsState()
    val cartItems = cartViewModel.cartItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        CartzyAppBar(
            title = "Cartzy",
            showCartIcon = true,
            showLogoutIcon = true,
            onCartClick = onCartClick,
            onLogoutClick = {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )

        Text(
            text = "Cart Items: ${cartItems.value.size}",
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(products.value) { product ->
                ProductItem(
                    product = product,
                    onClick = { /* Handle product item click */ },
                    onAddToCart = {
                        val cartItem = CartItem().apply {
                            id = product.id.toString()
                            productName = product.title
                            productId = product.id
                            price = product.price
                            quantity = 1
                            userId = currentUserId
                        }
                        cartViewModel.insertCartItem(currentUserId, cartItem)
                    }
                )
            }
        }
    }
}