package com.example.fundraisinghome

import ProductsRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Order
import com.example.fundraisinghome.model.UserSingleton
import com.example.fundraisinghome.ui.theme.CartViewModel
import com.example.fundraisinghome.ui.theme.FundRaisingHomeTheme



object Route {
    const val screenHome = "screenHome"
    const val screenBrowseAll = "screenBrowseAll"
    const val screenDetail = "screenDetail"
    const val screenCart = "screenCart"
    const val screenLogin = "screenLogin"
    const val screenRegister = "screenRegister"
    const val screenCheckout = "screenCheckout"
    const val screenOrderHistory = "screenOrderHistory"
    
}


class MainActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FundRaisingHomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Route.screenRegister){
                        composable(route= Route.screenHome){
                            HomeScreen(
                                navigateToScreenBrowseAll = {
                                    navController.navigate(Route.screenBrowseAll)
                                },
                                navigateToCart = {
                                    navController.navigate(Route.screenCart)
                                },
                                products = ProductsRepository.products,
                                navigateToscreenDetail = { index ->
                                    navController.navigate(route="screenDetail/$index")
                                },
                                navigateToscreenLogin = {navController.navigate(Route.screenLogin)},
                                navigateToScreenOrderHistory = {navController.navigate(Route.screenOrderHistory)}
                            )
                        }
                        composable(route= Route.screenBrowseAll){
                            BrowseScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                products = ProductsRepository.products,
                                navigateToscreenDetail = { index ->
                                    navController.navigate(route="screenDetail/$index")
                                },
                            )
                        }
                        composable(
                            route = "screenDetail/{index}",
                            arguments = listOf(
                                navArgument(name = "index") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: 0
                            val product = ProductsRepository.products[index]

                            DetailScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                product = product,
                                cartViewModel = cartViewModel
                            )
                        }
                        composable(route = Route.screenCart) {
                            CartScreen(
                                navigateBack = { navController.popBackStack()},
                                cartViewModel = cartViewModel,
                                navigateToCheckout = { totalPrice ->
                                navController.navigate("checkout/${String.format("%.2f", totalPrice)}")
                            })
                        }
                        composable(route= Route.screenLogin){
                            LoginScreen(
                                navigateToRegister = {
                                    navController.navigate(Route.screenRegister)
                                },
                                navigateToHome = {
                                    navController.navigate(Route.screenHome)
                                }
                            )
                        }
                        composable(route= Route.screenRegister){
                            RegisterScreen(
                                navigateToHome = {
                                    navController.navigate(Route.screenHome)
                                },
                                navigateToLogin = {
                                    navController.navigate(Route.screenLogin)
                                }
                            )
                        }
                        composable("checkout/{totalPrice}") { navBackStackEntry ->
                            val totalPrice = navBackStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0 // Extract total price from arguments



                            CheckoutScreen(
                                navigateBack = { navController.popBackStack() /* navigate back logic */ },
                                onCheckout = { orderDetails: Order ->
                                    // Perform checkout logic
                                    UserSingleton.addOrder(orderDetails)
                                },
                                totalPrice = totalPrice,
                                cartViewModel = cartViewModel
                            ) // Pass total price to CheckoutScreen,
                            { navController.navigate(Route.screenHome) }
                        }

                        composable(route= Route.screenOrderHistory){
                            OrderHistoryScreen(
                                navigateBack = { navController.popBackStack()}
                            )
                        }

                    }

                }
            }
        }
    }



}



