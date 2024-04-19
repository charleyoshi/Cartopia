/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.fundraisinghome.data

import android.content.Context



interface AppContainer {
    val productRepository: ProductRepository
    val orderRepository: OrderRepository
    val orderItemRepository: OrderItemRepository
    val cartItemRepository: CartItemRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val database = AppDatabase.getDatabase(context)

    override val productRepository: ProductRepository by lazy {
        ProductRepository(database.productDao())
    }

    override val orderRepository: OrderRepository by lazy {
        OrderRepository(database.orderDao(), database.orderItemDao())
    }


    override val orderItemRepository: OrderItemRepository by lazy {
        OrderItemRepository(database.orderItemDao())
    }

    override val cartItemRepository: CartItemRepository by lazy {
        CartItemRepository(database.cartItemDao())
    }

}