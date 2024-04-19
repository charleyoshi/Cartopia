package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.User
import com.example.fundraisinghome.model.UserSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartItemRepository(private val cartItemDao: CartItemDao) {

    suspend fun getCartItemsForCurrentUser(): List<CartItem> {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null) {
            return cartItemDao.getCartItemsForCurrentUser(currentUser.id.toString())
        }
        return emptyList()
    }

    suspend fun insertCartItem(cartItem: CartItem) {
        cartItemDao.insertCartItem(cartItem)
    }



    suspend fun clearCart() {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null) {
            cartItemDao.deleteAllCartItemsForUser(currentUser.id.toString())
        }
    }

    suspend fun addToCart(product: Product, quantity: Int) {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.id.toString()
            val existingCartItem = cartItemDao.getCartItemByUserIdAndProductId(userId, product.productId)
            if (existingCartItem != null) {
                // If the cart item already exists, update its quantity
                val updatedQuantity = existingCartItem.quantity + quantity
                cartItemDao.updateCartItemQuantity(userId = userId, productId = product.productId, updatedQuantity)
            } else {
                // If the cart item does not exist, insert a new one
                val newCartItem = CartItem(userId = userId, productId = product.productId, quantity = quantity)
                cartItemDao.insertCartItem(newCartItem)
            }
        }
    }

    suspend fun increaseQuantity(cartItem: CartItem){
        val existingCartItem = cartItemDao.getCartItemByUserIdAndProductId(cartItem.userId, cartItem.productId)
        if (existingCartItem != null) {
            val updatedQuantity = existingCartItem.quantity + 1
            cartItemDao.updateCartItemQuantity(userId = cartItem.userId, productId = cartItem.productId, updatedQuantity)
        }
    }


    suspend fun decreaseQuantity(cartItem: CartItem) {
        val existingCartItem = cartItemDao.getCartItemByUserIdAndProductId(cartItem.userId, cartItem.productId)
        if (existingCartItem != null) {
            val updatedQuantity = existingCartItem.quantity - 1
            cartItemDao.updateCartItemQuantity(userId = cartItem.userId, productId = cartItem.productId, updatedQuantity)
        }
    }


    suspend fun deleteCartItem(cartItem: CartItem) {
        cartItemDao.deleteCartItem(cartItem)
    }

    suspend fun calculateTotalPrice(): Double {
        val currentUser = UserSingleton.getCurrentUser()
        return if (currentUser != null) {
            cartItemDao.calculateTotalPrice(currentUser.id.toString())
        } else {
            0.0 // Return 0 if no current user is logged in
        }
    }

}

