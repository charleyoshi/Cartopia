package com.example.fundraisinghome.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.data.ProductRepository
import com.example.fundraisinghome.model.CartItem
import com.example.fundraisinghome.model.Product
import com.example.fundraisinghome.ui.theme.CartViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    cartViewModel: CartViewModel,
    navigateToCheckout: (Double) -> Unit,
    productRepository: ProductRepository
) {

    val cartUiState by cartViewModel.uiState.collectAsState()

    // State to track the item being deleted
    val deletingItem = remember { mutableStateOf<CartItem?>(null) }
    cartViewModel.updateUiState()

    // Fetch all products and create product map
    val products by remember {
        mutableStateOf(runBlocking {
            productRepository.getAllProducts()
        })
    }
    val productMap = remember {
        mutableStateOf(products.associateBy { it.productId })
    }

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
    // Check if cart is empty
        if (cartUiState.items.isEmpty()) {
            item {
                Text(
                    text = "Your cart is empty", textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

            }
        } else {
        // Cart List Items
        itemsIndexed(cartUiState.items) { index, cartItem ->
            key(cartItem.hashCode()) {
                CartListItem(
                    productMap = productMap,
                    cartItem = cartItem,
                    onIncrease = { cartViewModel.increaseQuantity(cartItem) },
                    onDecrease = {cartViewModel.decreaseQuantity(cartItem) },
                    deletingItem = deletingItem,
                    imageResId = cartItem.productId
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
    }
}

@Composable
fun CartListItem(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    imageResId: String,
    modifier: Modifier = Modifier,
    productMap: MutableState<Map<String, Product>>,
    deletingItem: MutableState<CartItem?>
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
                    painter = painterResource(LocalContext.current.resources.getIdentifier(imageResId, "drawable", LocalContext.current.packageName)),
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
                    text = productMap.value[cartItem.productId]?.name ?: "Product Name Not Found",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "$${productMap.value[cartItem.productId]?.price ?: "0"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Sub-total: $${cartItem.quantity * (productMap.value[cartItem.productId]?.price ?: 0.0)}",
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
                IconButton(onClick = {
                    if (cartItem.quantity > 1) {
                        onDecrease()
                    } else {
                        deletingItem.value = cartItem // Update deletingItem state
                    }
                }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                }
            }
        }
    }
}

