package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundraisinghome.model.OrderItem
import com.example.fundraisinghome.model.Order_
import com.example.fundraisinghome.model.PaymentDetails_
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.ShippingDetails_
import com.example.fundraisinghome.model.User


@Dao
interface PaymentDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentDetails(paymentDetails: PaymentDetails_): Long

    // Add more methods as needed
}
