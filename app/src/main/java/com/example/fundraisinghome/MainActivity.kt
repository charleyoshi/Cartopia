package com.example.fundraisinghome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fundraisinghome.data.AppContainer
import com.example.fundraisinghome.data.AppDatabase
import com.example.fundraisinghome.data.CartItemRepository
import com.example.fundraisinghome.data.OrderItemRepository
import com.example.fundraisinghome.data.OrderRepository
import com.example.fundraisinghome.data.PaymentDetailsRepository
import com.example.fundraisinghome.data.ProductRepository
import com.example.fundraisinghome.data.ShippingDetailsRepository
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.ui.screen.BrowseScreen
import com.example.fundraisinghome.ui.screen.CartScreen
import com.example.fundraisinghome.ui.screen.CheckoutScreen
import com.example.fundraisinghome.ui.screen.DetailScreen
import com.example.fundraisinghome.ui.screen.HomeScreen
import com.example.fundraisinghome.ui.screen.LoginScreen
import com.example.fundraisinghome.ui.screen.OrderHistoryScreen
import com.example.fundraisinghome.ui.screen.RegisterScreen
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

    private val appContainer: AppContainer by lazy {
        (applicationContext as CartopiaApplication).container
    }

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
                    val database = AppDatabase.getDatabase(LocalContext.current)
                    val productRepository = appContainer.productRepository
                    val orderRepository = appContainer.orderRepository
                    val cartItemRepository = appContainer.cartItemRepository
                    val orderItemRepository = appContainer.orderItemRepository

                    val cartViewModel = CartViewModel(cartItemRepository)
                    var products = remember { mutableStateOf<List<Product>>(emptyList()) }

                    LaunchedEffect(key1 = Unit) {
                        val productList = productRepository.getAllProducts()
                        products.value = productList
                    }
                    NavHost(navController = navController, startDestination = Route.screenRegister){
                        composable(route= Route.screenHome){

                            HomeScreen(
                                navigateToScreenBrowseAll = {
                                    navController.navigate(Route.screenBrowseAll)
                                },
                                navigateToCart = {
                                    navController.navigate(Route.screenCart)
                                },
                                products = products.value,
                                navigateToscreenDetail = { index ->
                                    navController.navigate(route="screenDetail/$index")
                                },
                                navigateToscreenLogin = {navController.navigate(Route.screenLogin)},
                                navigateToScreenOrderHistory = {navController.navigate(Route.screenOrderHistory)}
                            )
                        }
                        composable(route= Route.screenBrowseAll){

                            LaunchedEffect(key1 = Unit) {
                                val productList = productRepository.getAllProducts()
                                products.value = productList
                            }
                            BrowseScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                products = products.value,
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
//                            val product = ProductsRepository.products[index]

                            DetailScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                product = products.value[index],
                                cartViewModel = cartViewModel
                            )
                        }
                        composable(route = Route.screenCart) {
                            CartScreen(
                                navigateBack = { navController.popBackStack()},
                                productRepository = productRepository,
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


                            val shippingDetailsDao = database.shippingDetailsDao()
                            val shippingDetailsRepository = ShippingDetailsRepository(shippingDetailsDao)
                            val paymentDetailsDao = database.paymentDetailsDao()
                            val paymentDetailsRepository = PaymentDetailsRepository(paymentDetailsDao)
                            CheckoutScreen(
                                navigateBack = { navController.popBackStack() /* navigate back logic */ },
//                                onCheckout = {() -> Unit },
                                orderRepository = orderRepository,
                                orderItemRepository = orderItemRepository,
                                shippingDetailsRepository = shippingDetailsRepository,
                                paymentDetailsRepository = paymentDetailsRepository,
                                totalPrice = totalPrice,
                                cartViewModel = cartViewModel
                            ) // Pass total price to CheckoutScreen,
                            { navController.navigate(Route.screenHome) }
                        }

                        composable(route= Route.screenOrderHistory){
                            OrderHistoryScreen(
                                navigateBack = { navController.popBackStack()},
                                orderRepository = orderRepository,
                                productRepository = productRepository
                            )
                        }


                    }


                }
            }
        }
    }



}



