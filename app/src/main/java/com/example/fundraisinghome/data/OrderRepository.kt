package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.OrderItem
import com.example.fundraisinghome.model.Order_
import com.example.fundraisinghome.model.PaymentDetails_
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.ShippingDetails_
import com.example.fundraisinghome.model.User
import com.example.fundraisinghome.model.UserSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext// OrderRepository.kt
class OrderRepository(private val orderDao: OrderDao, private val orderItemDao: OrderItemDao) {
    suspend fun insertOrder(order: Order_) {
        orderDao.insertOrder(order)
    }

    suspend fun getOrdersForUser(): List<Order_> {
        val currentUser = UserSingleton.getCurrentUser()
        if (currentUser != null)
        {
            return orderDao.getOrdersForUser(currentUser.id.toString())
        }
        return emptyList()
    }

    suspend fun getOrderItemsForOrder(orderId: String): List<OrderItem>{
        return orderItemDao.getOrderItemsForOrder(orderId)
    }
}

// OrderItemRepository.kt
class OrderItemRepository(private val orderItemDao: OrderItemDao) {
    suspend fun insertOrderItem(orderItem: OrderItem) {
        orderItemDao.insertOrderItem(orderItem)
    }

    suspend fun getOrderItemsForOrder(orderId: String): List<OrderItem> {
        return orderItemDao.getOrderItemsForOrder(orderId)
    }
}

// ShippingDetailsRepository.kt
class ShippingDetailsRepository(private val shippingDetailsDao: ShippingDetailsDao) {
    suspend fun insertShippingDetails(shippingDetails: ShippingDetails_): Long {
        return shippingDetailsDao.insertShippingDetails(shippingDetails)
    }
}

// PaymentDetailsRepository.kt
class PaymentDetailsRepository(private val paymentDetailsDao: PaymentDetailsDao) {
    suspend fun insertPaymentDetails(paymentDetails: PaymentDetails_): Long {
        return paymentDetailsDao.insertPaymentDetails(paymentDetails)
    }
}
