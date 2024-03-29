package com.example.fundraisinghome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.ui.theme.CartViewModel


//                }
//



@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    cartViewModel: CartViewModel,
    navigateToCheckout: (Double) -> Unit
) {
    val cartUiState by cartViewModel.uiState.collectAsState()

    // State to track the item being deleted
    val deletingItem = remember { mutableStateOf<CartItem?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top Bar
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back") // Back arrow icon
                }
                Text(
                    text = "Your Cart",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 18.dp)
                )
            }
        }
    // Check if cart is empty
        if (cartUiState.items.isEmpty()) {
            item {
                Text(
                    text = "Your cart is empty", textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)


                )
            }
        } else {
        // Cart List Items
        itemsIndexed(cartUiState.items) { index, cartItem ->
            key(cartItem.hashCode()) {
                CartListItem(
                    cartItem = cartItem,
                    onIncrease = { cartViewModel.increaseQuantity(cartItem) },
                    onDecrease = {
                        cartViewModel.decreaseQuantity(cartItem) {
                            // Show confirmation dialog when deleting item
                            deletingItem.value = cartItem
                        }
                    },
                    imageResId = cartItem.product.imageRes
                )
            }
        }}
        // Total Price and Checkout button
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Total Price: ${String.format("%.2f", cartUiState.totalPrice)}",
                    modifier = Modifier.padding(top = 16.dp)
                )
                if (cartUiState.totalPrice > 0) {
                    Button(
                        onClick = { navigateToCheckout(cartUiState.totalPrice) },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Checkout")
                    }
                }

            }
        }

        // Confirmation dialog for item deletion
        if (deletingItem.value != null) {
            item {
                AlertDialog(
                    onDismissRequest = { deletingItem.value = null },
                    title = { Text("Delete Item") },
                    text = { Text("Are you sure you want to delete this item?") },
                    confirmButton = {
                        Button(onClick = {
                            cartViewModel.deleteItem(deletingItem.value!!)
                            deletingItem.value = null
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { deletingItem.value = null }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CartListItem(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.padding(vertical = 2.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp)
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.FillBounds
                )
            }

            // Product details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp)
            ) {
                Text(
                    text = stringResource(id = cartItem.product.nameRes),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "$${cartItem.product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Sub-total: $${cartItem.quantity * cartItem.product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Quantity adjustment buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
                }
                Text(
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.padding(vertical = 0.dp)
                )
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    // Create a dummy CartViewModel for preview
    val dummyCartViewModel = CartViewModel()

    // Call the CartScreen composable with required parameters
//    CartScreen(
//        navigateBack = {},
//        cartViewModel = dummyCartViewModel,
//        navigateToCheckout: {  }
//    )
}
