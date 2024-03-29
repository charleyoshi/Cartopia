package com.example.fundraisinghome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.Product


@Composable
fun BrowseScreen(
    products: List<Product>,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToscreenDetail: (Int)-> Unit,

) {
    var searchText = remember { mutableStateOf("") }
    // Filter the list of products based on the search text
    val filteredProducts = if (searchText.value.isNotBlank()) {
        products.filter { product ->
            // Get the string value of the resource ID
            val productName = stringResource(product.nameRes)
            // Filter logic: Check if the product name contains the search text (case-insensitive)
            productName.contains(searchText.value, ignoreCase = true)

        }
    } else {
        // If search text is empty, show all products
        products
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navigateBack() },
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back") // Back arrow icon
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.browse_all_products),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.weight(1f).padding(start = 18.dp)
            )
        }
        // Search Bar
        OutlinedTextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            label = { Text("Search products") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        )

        // Products List
        ProductsList(products = filteredProducts, navigateToscreenDetail = navigateToscreenDetail)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductsList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigateToscreenDetail: (Int) -> Unit,
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 items per row
            contentPadding = contentPadding,
            modifier = modifier
        ) {
            itemsIndexed(products) { index, product ->
                ProductListItem(
                    product = product,
                    itemIndex = index,
                    navigateToscreenDetail = navigateToscreenDetail,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        // Animate each list item to slide in vertically
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        )
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(
    product: Product,
    itemIndex:Int,
    modifier: Modifier = Modifier,
    navigateToscreenDetail: (Int) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
        onClick = {navigateToscreenDetail(itemIndex)},// Make the card clickable
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Box(
                modifier = Modifier
                    .size(102.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )

            }
            Spacer(Modifier.height(22.dp))
            Text(
                text = stringResource(product.nameRes),
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.bodyLarge

            )
        }
    }
}


//@Preview("Light Theme")
//@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun ProductListsPreview() {
//    val product = Product(
//        R.string.product1,
//        R.string.product_description1,
//        R.drawable.product1,
//        R.string.product_price1
//    )
//        ProductListItem(product = product, navigateToscreenDetail = {} )
//
//}
