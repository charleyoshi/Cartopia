package com.example.fundraisinghome.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fundraisinghome.model.Product
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Snackbar
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.fundraisinghome.ui.theme.CartViewModel


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    product: Product,
    cartViewModel: CartViewModel
) {
    val messageState = remember { mutableStateOf<String?>(null) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().height(23.dp)
        ) {
            IconButton(
                onClick = { navigateBack() },
                modifier = Modifier.size(38.dp).padding(top=4.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back") // Back arrow icon
            }
            Text(
                text = "Product Detail",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.weight(1f)
                    .padding(start = 18.dp)
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(LocalContext.current.resources.getIdentifier(product.productId, "drawable", LocalContext.current.packageName)),
                contentDescription = product.name, // Assuming nameRes is a string resource ID
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }
        Text(text = product.name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text(text = product.price.toString() , fontSize = 18.sp)
        Text(text = product.description, fontSize = 18.sp)


        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AddToCartButton(
                onClick = {
                    cartViewModel.addToCart(product = product, quantity = 1)
                    messageState.value = "Added to cart"
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(Alignment.BottomStart) // Align the Snackbar to the bottom left
    ) {
        messageState.value?.let { message ->
            Snackbar(
                modifier = Modifier.wrapContentSize(Alignment.BottomStart), // Wrap content and align to the bottom left
                action = {
                    TextButton(onClick = { messageState.value = null }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(message)
            }
        }
    }
}



@Composable
fun AddToCartButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Add to Cart")
    }
}
//class CartViewModel : ViewModel() {
//    private val _cartItems = mutableStateListOf<Product>()
//    val cartItems: List<Product> get() = _cartItems
//
//    fun addToCart(product: Product) {
//        _cartItems.add(product)
//    }
//
//
//}

