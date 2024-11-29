package com.example.cartzyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cartzyapp.presentation.CartScreen
import com.example.cartzyapp.presentation.HomeScreen
import com.example.cartzyapp.presentation.UserLogin
import com.example.cartzyapp.ui.theme.CartzyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartzyAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        composable(Route.Login.route) {
            UserLogin(navController = navController)
        }

        composable(
            route = Route.Home.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HomeScreen(
                navController = navController,
                currentUserId = userId,
                onCartClick = {
                    navController.navigate(Route.Cart.createRoute(userId))
                }
            )
        }


        composable(
            route = Route.Cart.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            CartScreen(navController = navController, currentUserId = userId)
        }
    }
}
