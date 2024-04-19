package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartitem_table WHERE userId = :userId")
    suspend fun getCartItemsForCurrentUser(userId: String): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)


    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cartitem_table WHERE userId = :userId")
    suspend fun deleteAllCartItemsForUser(userId: String)

    @Query("SELECT * FROM cartitem_table WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun getCartItemByUserIdAndProductId(userId: String, productId: String): CartItem?

    @Query("UPDATE cartitem_table SET quantity = :newQuantity WHERE userId = :userId AND productId = :productId")
    suspend fun updateCartItemQuantity(userId: String, productId: String, newQuantity: Int)


    @Query("SELECT SUM(p.price * c.quantity) FROM cartitem_table c INNER JOIN product_table p ON c.productId = p.productId WHERE c.userId = :userId")
    suspend fun calculateTotalPrice(userId: String): Double


}