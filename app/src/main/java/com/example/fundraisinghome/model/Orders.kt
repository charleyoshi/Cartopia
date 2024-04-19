package com.example.fundraisinghome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class Order_(
    @PrimaryKey
    val orderId: String,
    val userId: String,
    val totalPrice: Double,
    val date: String,
    val shippingDetailsId: String,
    val paymentDetailsId: String,
    val status: OrderStatus
)

@Entity(tableName = "orderitem_table")
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderId: String,
    val productId: String,
    val quantity: Int
)

@Entity
data class ShippingDetails_(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String
)

@Entity
data class PaymentDetails_(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String
)
