package com.example.fundraisinghome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.Order
import com.example.fundraisinghome.model.OrderStatus
import com.example.fundraisinghome.model.PaymentDetails
import com.example.fundraisinghome.model.ShippingDetails
import com.example.fundraisinghome.model.UserSingleton
import com.example.fundraisinghome.ui.theme.CartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onCheckout: (Order) -> Unit,
    totalPrice: Double,
    cartViewModel: CartViewModel,
    navigateToScreenHome: () -> Unit,
) {
    val cartUiState by cartViewModel.uiState.collectAsState()

    val name = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    var city = remember { mutableStateOf("") }
    var postalCode = remember { mutableStateOf("") }
    var cardHolderName = remember { mutableStateOf("") }
    var cardNumber = remember { mutableStateOf("") }
    var expirationDate = remember { mutableStateOf("") }
    var cvv = remember { mutableStateOf("") }
    var showCompleteDialog by remember { mutableStateOf(false) }


    // Validation functions for credit card number, expiration date, and CVV
    fun isValidCreditCardNumber(number: String): Boolean {
        return number.matches(Regex("\\d{16}"))
    }

    fun isValidExpirationDate(date: String): Boolean {
        return date.matches(Regex("\\d{2}/\\d{2}"))
    }

    fun isValidCVV(cvv: String): Boolean {
        return cvv.matches(Regex("\\d{3}"))
    }

    // Check if any field is empty
    val isFormValid = listOf(
        name.value,
        address.value,
        city.value,
        postalCode.value,
        cardHolderName.value,
        cardNumber.value,
        expirationDate.value,
        cvv.value
    ).all { it.isNotBlank() } && isValidExpirationDate(expirationDate.value) &&
            cvv.value.isNotBlank() &&
            isValidCVV(cvv.value)



    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navigateBack() },
                modifier = Modifier.padding(start = 0.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Checkout",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.displayMedium,
            )
        }

        // Shipping details
        Text(
            text = "Shipping Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        TextField(
            value = name.value, // use state for actual values
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = city.value,
            onValueChange = { city.value = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = postalCode.value,
            onValueChange = { postalCode.value = it },
            label = { Text("Postal Code") },
            modifier = Modifier.fillMaxWidth()
        )

        // Credit card details
        Text(
            text = "Credit Card Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp),

        )
        TextField(
            value = cardHolderName.value,
            onValueChange = { cardHolderName.value = it },
            label = { Text("Card Holder Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = cardNumber.value,
            onValueChange = { cardNumber.value = it },
            label = { Text("Credit Card Number") },
                    modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = expirationDate.value,
                onValueChange = { expirationDate.value = it },
                label = { Text("Expires (MM/YY)") },
                modifier = Modifier.weight(0.46f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = cvv.value,
                onValueChange = { cvv.value = it },
                label = { Text("CVV") },
                modifier = Modifier.weight(0.46f)
            )
        }
        Text(
            text = "Total: $${String.format("%.2f", totalPrice)}",
            style = MaterialTheme.typography.displayMedium,
            modifier=Modifier.padding(top = 16.dp)
        )
        // Checkout button
        Button(
            onClick = {showCompleteDialog = true},
            enabled = isFormValid,
                    modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text(text = "Complete")
        }
    }

    // Logout AlertDialog
    if (showCompleteDialog) {
        AlertDialog(
            onDismissRequest = { showCompleteDialog = true },
            title = { Text("Order Complete!") },
            text = { Text("You can find your order in order history.") },
            confirmButton = {
                Button(
                    onClick = {
                        showCompleteDialog = false
//                        onCheckout() : todo
                        val shippingDetails = ShippingDetails(
                            name = name.value,
                            address = address.value,
                            city = city.value,
                            postalCode = postalCode.value
                        )
                        val paymentDetails = PaymentDetails(
                            cardHolderName = cardHolderName.value,
                            cardNumber = cardNumber.value,
                            expirationDate = expirationDate.value,
                            cvv = cvv.value
                        )
                        val items = cartUiState.items

                        // Calculate total price
                        val totalPrice = items.sumByDouble { it.product.price * it.quantity }

                        // Get current date
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                        // Create Order object
                        val order = Order(
                            orderId = UUID.randomUUID().toString(), // Generate unique order ID
                            userId = UserSingleton.getCurrentUser()?.userId ?: "", // Get current user ID
                            items = items,
                            totalPrice = totalPrice,
                            date = currentDate,
                            shippingDetails = shippingDetails,
                            paymentDetails = paymentDetails,
                            status = OrderStatus.Pending
                        )

                        // Call the checkout function
                        onCheckout(order)
                        cartViewModel.resetCart()
                        navigateToScreenHome()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
