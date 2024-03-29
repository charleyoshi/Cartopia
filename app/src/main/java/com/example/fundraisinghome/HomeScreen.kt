package com.example.fundraisinghome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.Product
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import com.example.fundraisinghome.model.UserSingleton

@Composable
fun HomeScreen(navigateToScreenBrowseAll: ()-> Unit,
               navigateToscreenDetail: (Int) -> Unit,
               navigateToscreenLogin: () -> Unit,
               navigateToScreenOrderHistory: () -> Unit,
               navigateToCart: () -> Unit,
               products: List<Product>,
               contentPadding: PaddingValues = PaddingValues(8.dp)) {


    var currentImageIndex by remember { mutableStateOf(0) }
    // State variable to track if the logout dialog should be shown
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp)

    ) {
        // Banner Image
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp, top=0.dp, bottom = 0.dp)
            )
            // Cart icon
            IconButton(
                onClick = { navigateToScreenOrderHistory() },
                modifier = Modifier.size(22.dp)
            ) {
                Icon(painterResource(R.drawable.history_icon), contentDescription = "Order History")
            }
            Spacer(Modifier.width(14.dp))
            // Cart icon
            IconButton(
                        onClick = { navigateToCart() },
                        modifier = Modifier.size(22.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
            Spacer(Modifier.width(14.dp))
            IconButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.size(22.dp)) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
            Spacer(Modifier.width(18.dp))

        }
        FeaturedImage(currentImageIndex)

        // Featured Products Title and Navigation
        FeaturedProductsNavigation(
            navigateToScreenBrowseAll = navigateToScreenBrowseAll,
            onScrollLeftClick = {
                // Update image index when left arrow is clicked
                currentImageIndex = (currentImageIndex - 1 + 3) % 3
            },
            onScrollRightClick = {
                // Update image index when right arrow is clicked
                currentImageIndex = (currentImageIndex + 1) % 3
            }
        )

        // Horizontal Scrollable List of First 4 Products
        HorizontalProductList(products.take(4), contentPadding = contentPadding,
            navigateToscreenDetail = navigateToscreenDetail)

    }


    // Logout AlertDialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        // Call the logout function and navigate to login screen
                        UserSingleton.logoutUser()
                        navigateToscreenLogin()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}


@Composable
fun FeaturedImage(currentIndex: Int) {
    val imageResource = when (currentIndex) {
        0 -> R.drawable.feature_image01
        1 -> R.drawable.feature_image02
        else -> R.drawable.feature_image03
    }

    Image(
        painter = painterResource(imageResource),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(vertical = 0.dp, horizontal = 12.dp)
    )

}

@Composable
fun FeaturedProductsNavigation(navigateToScreenBrowseAll: ()-> Unit, onScrollLeftClick: () -> Unit, onScrollRightClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onScrollLeftClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Scroll Left")
            }
            IconButton(onClick = onScrollRightClick) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Scroll Right")
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Featured Products",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.weight(1f)
            )
            ClickableText(
                text = AnnotatedString("See all"),
                onClick = {navigateToScreenBrowseAll()},
                style = MaterialTheme.typography.displayMedium
                    .copy(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
    }
}


@Composable
fun HorizontalProductList(products: List<Product>, navigateToscreenDetail: (Int) -> Unit, contentPadding: PaddingValues) {
    LazyRow(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size) { index ->
            val product = products[index]
            ProductListItem(product = product, itemIndex = index, navigateToscreenDetail = navigateToscreenDetail)
        }
    }
}





@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val products = ProductsRepository.products
//    HorizontalProductList(products.take(4), contentPadding = PaddingValues(0.dp))
//    HomeScreen(products = products, contentPadding = PaddingValues(0.dp))
    HomeScreen(
        navigateToScreenBrowseAll = { /*TODO*/ },
        navigateToscreenDetail = { /*TODO*/ },
        navigateToCart = { /*TODO*/ },
        products = products,
        navigateToscreenLogin = {/*TODO*/ },
        navigateToScreenOrderHistory = { /*TODO() */}
    )


}