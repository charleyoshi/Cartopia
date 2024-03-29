package com.example.fundraisinghome

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.Order
import com.example.fundraisinghome.model.UserSingleton

@Composable
fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val orders = UserSingleton.retrieveOrdersForCurrentUser()

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
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 300.dp)
                )
            }
        } else {
            // Cart List Items
            itemsIndexed(orders) { index, orderItem ->
                key(orderItem.hashCode()) {
                    OrderItem(orderItem
                    )
                }
            }}


    }

}

@Composable
fun OrderItem(order: Order) {
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
            order.items.forEach { item ->
                Text(text = "${stringResource(id = item.product.nameRes)}   -   ${item.quantity}")
            }
            Text(text = "Total Price: $${order.totalPrice}")
            Text(text = "Order Date: ${order.date}")
            Text(text = "Status: ${order.status.name}")
        }
    }
}
