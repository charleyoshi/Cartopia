package com.example.fundraisinghome.ui.screen//package com.example.fundraisinghome
//
//
//import android.media.Image
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun PaymentScreen(
//    onPaymentComplete: () -> Unit
//) {
//    var cardNumber by remember { mutableStateOf("") }
//    var expiryDate by remember { mutableStateOf("") }
//    var cvv by remember { mutableStateOf("") }
//    var cardHolderName by remember { mutableStateOf("") }
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        // Background Image
//        Image(
//            painter = painterResource(id = R.drawable.back),
//            contentDescription = "Background Image",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds // Adjust content scale as needed
//        )
//
//    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.pay),
//            contentDescription = "Home Image",
//            modifier = Modifier.padding(2.dp)
//                .fillMaxWidth()
//                .padding(0.dp)
//                .size(200.dp)
//        )
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.xxx),
//            contentDescription = "Home Image",
//            modifier = Modifier
//                .size(200.dp)
//                .padding(8.dp)
//        )
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Left Column
//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
//                    OutlinedTextField(
//                        value = cardNumber,
//                        onValueChange = { cardNumber = it },
//                        label = { Text("Card Number") },
//                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    OutlinedTextField(
//                        value = expiryDate,
//                        onValueChange = { expiryDate = it },
//                        label = { Text("Expiry Date") },
//                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                // Right Column
//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
//                    OutlinedTextField(
//                        value = cvv,
//                        onValueChange = { cvv = it },
//                        label = { Text("CVV") },
//                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    OutlinedTextField(
//                        value = cardHolderName,
//                        onValueChange = { cardHolderName = it },
//                        label = { Text("Cardholder's Name") },
//                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
//        }
//        Button(
//            onClick = {
//                // Simulate payment processing
//                // Here you would integrate with a payment gateway
//                // For the sake of this example, let's just log the payment details
//                println("Payment Details:")
//                println("Card Number: $cardNumber")
//                println("Expiry Date: $expiryDate")
//                println("CVV: $cvv")
//
//                // Notify payment complete
//                onPaymentComplete()
//            }
//        ) {
//            Text("Submit Payment")
//        }
//    }
//}
//
