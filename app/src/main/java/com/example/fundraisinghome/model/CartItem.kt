package com.example.fundraisinghome.model

data class CartItem(
    val userId: String, // User ID associated with this cart item
    val product: Product,
    var quantity: Int
)
