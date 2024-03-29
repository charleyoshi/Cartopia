package com.example.fundraisinghome.model

data class Order(
    var orderId: String, // Unique identifier for the order
    val userId: String, // User ID associated with this order
    val items: List<CartItem>, // List of items in the order
    val totalPrice: Double, // Total price of the order
    val date: String, // Date of the order
    val shippingDetails: ShippingDetails,
    val paymentDetails: PaymentDetails,
    val status: OrderStatus
)

data class ShippingDetails(
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String
)

data class PaymentDetails(
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String
)


enum class OrderStatus {
    Shipped,
    Pending,
    Complete,
    // Add more status values as needed
}