package com.example.fundraisinghome.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.data.OrderRepository
import com.example.fundraisinghome.model.Order_

import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.fundraisinghome.data.ProductRepository
import com.example.fundraisinghome.model.OrderItem
import com.example.fundraisinghome.model.Product
import kotlinx.coroutines.launch

@Composable
fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    orderRepository: OrderRepository,
    productRepository: ProductRepository
) {

//    val orders = UserSingleton.retrieveOrdersForCurrentUser()
    var orders by remember { mutableStateOf<List<Order_>>(emptyList()) }
    LaunchedEffect(Unit) {
        orders = orderRepository.getOrdersForUser()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Order History",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        // Check if cart is empty
        if (orders.isEmpty()) {
            item {
                Text(
                    text = "Your order history is empty", textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 300.dp)
                )
            }
        } else {
            // Orders
            items(orders) { order ->
                OrderReceipt(order = order, productRepository = productRepository, orderRepository = orderRepository)
            }
        }
    }
}


@Composable
fun OrderReceipt(order: Order_, productRepository : ProductRepository, orderRepository : OrderRepository) {
    var orderItems by remember { mutableStateOf<List<OrderItem>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val items = orderRepository.getOrderItemsForOrder(order.orderId)
            orderItems = items
        }
    }


    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Order ID: #${order.orderId}", fontWeight = FontWeight.Bold
                ,style = MaterialTheme.typography.displayMedium)
            Text(text = "")

            // Display order items
            orderItems.forEach { orderItem ->
                PurchaseItem(orderItem = orderItem, productRepository = productRepository)
            }
            Text(text = "Total Price: $${order.totalPrice}")
            Text(text = "Order Date: ${order.date}")
            Text(text = "Status: ${order.status.name}")
        }
    }
}

@Composable
fun PurchaseItem(orderItem: OrderItem, productRepository : ProductRepository) {

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    LaunchedEffect(Unit) {
        product = productRepository.getProductById(orderItem.productId)
    }
    product?.let { product ->
        Text(text = "Product: ${product.name} - Quantity: ${orderItem.quantity}")
    }

}
