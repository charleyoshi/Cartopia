package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.model.User
import com.example.fundraisinghome.model.UserSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productDao: ProductDao) {

    suspend fun getAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            productDao.getAllProducts()
        }
    }

    suspend fun getProductById(productId: String): Product? {
        return productDao.getProductById(productId)
    }


}
