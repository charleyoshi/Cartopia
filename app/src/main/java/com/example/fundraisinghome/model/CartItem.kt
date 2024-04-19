package com.example.fundraisinghome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartitem_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String, // User ID associated with this cart item
    val productId: String,
    var quantity: Int
)
