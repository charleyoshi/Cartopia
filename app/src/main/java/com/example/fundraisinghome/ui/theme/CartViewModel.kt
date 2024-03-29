package com.example.fundraisinghome.ui.theme

import androidx.compose.animation.core.updateTransition
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Product
import androidx.lifecycle.ViewModel
import com.example.fundraisinghome.model.UserSingleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CartViewModel : ViewModel() {
    // Game UI state
    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _cartItems = mutableListOf<CartItem>()

    init {
        UserSingleton.setCartViewModel(this)
    }

    // Function to reset the cart
    fun resetCart() {
        _cartItems.clear()
        updateUiState()
    }


    fun addToCart(product: Product, quantity: Int) {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.userId
            // Check if the product already exists in the cart for this user
            val existingCartItem = _cartItems.find { it.userId == userId && it.product == product }

            if (existingCartItem != null) {
                // If the product already exists, update the quantity
                existingCartItem.quantity += quantity
            } else {
                // If the product does not exist, add it to the cart
                _cartItems.add(CartItem(userId, product, quantity))
            }

            // Update the UI state after modifying the cart items
            updateUiState(userId)
        } else {
            // Handle the case when no user is logged in
        }
    }

    // Function to increase the quantity of a cart item
    fun increaseQuantity(cartItem: CartItem) {
        cartItem.quantity++
        updateUiState()
    }
    // Function to decrease the quantity of a cart item
    fun decreaseQuantity(cartItem: CartItem, onDeleteItem: () -> Unit) {
        if (cartItem.quantity > 1) {
            cartItem.quantity--
            updateUiState()
        } else {
            // Show confirmation dialog
            // Pass onDeleteItem callback to the dialog
            // This callback will be called when the user confirms deletion
            onDeleteItem()
        }
    }

    // Function to delete an item from the cart
    fun deleteItem(cartItem: CartItem) {
        _cartItems.remove(cartItem)
        updateUiState()
    }


    fun updateUiState(userId: String="") {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null) {
            _uiState.value =
                CartUiState(items = getCartItemsForUser(currentUser.userId), totalPrice = calculateTotalPrice(currentUser.userId))
        }
    }

    private fun calculateTotalPrice(userId: String): Double {
        val itemsForUser = getCartItemsForUser(userId)
        return itemsForUser.sumByDouble { it.product.price * it.quantity }
    }




    private fun getCartItemsForUser(userId: String): List<CartItem> {
        // Filter the cart items based on the provided userId
//        _uiState.value = CartUiState(items = _cartItems.filter { it.userId == userId })
        return _cartItems.filter { it.userId == userId }
    }
}