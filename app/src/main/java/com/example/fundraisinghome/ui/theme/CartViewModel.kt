package com.example.fundraisinghome.ui.theme

import androidx.compose.runtime.MutableState
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundraisinghome.data.CartItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException


class CartViewModel(private val cartItemRepository: CartItemRepository) : ViewModel() {
    // Cart UI state
    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _cartItems = mutableListOf<CartItem>()

    init {
        updateUiState()
    }



    // Function to reset the cart
    fun resetCart() {
        viewModelScope.launch {
            cartItemRepository.clearCart()
            updateUiState()
        }
    }


    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            cartItemRepository.addToCart(product, quantity)
            updateUiState()
        }

    }

    // Function to increase the quantity of a cart item
    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.increaseQuantity(cartItem)
            updateUiState()
        }
    }


    // Function to decrease the quantity of a cart item
    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity > 1) {
                cartItemRepository.decreaseQuantity(cartItem)
                updateUiState()
            }
        }
    }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.deleteCartItem(cartItem)
            updateUiState()
        }
    }


    fun updateUiState(userId: String="") {
        viewModelScope.launch {
            val cartItems = cartItemRepository.getCartItemsForCurrentUser()
            val totalPrice = cartItemRepository.calculateTotalPrice()
            _uiState.value = CartUiState(items = cartItems, totalPrice = totalPrice)
        }
    }


}