package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.User


// Define the Product DAO (Data Access Object)
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product_table WHERE productId = :productId")
    suspend fun getProductById(productId: String): Product?

    @Query("SELECT * FROM product_table LIMIT 1 OFFSET :index")
    suspend fun getProductByIndex(index: Int): Product

}