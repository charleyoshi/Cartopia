package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundraisinghome.model.OrderItem
import com.example.fundraisinghome.model.Order_
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.User


// Define the Product DAO (Data Access Object)
@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order_)

    @Query("SELECT * FROM order_table WHERE userId = :userId")
    suspend fun getOrdersForUser(userId: String): List<Order_>


}

