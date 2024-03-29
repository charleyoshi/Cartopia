package com.example.fundraisinghome.ui.theme

import com.example.fundraisinghome.model.CartItem

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0
)
