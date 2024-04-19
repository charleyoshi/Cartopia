package com.example.fundraisinghome.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.R
import com.example.fundraisinghome.data.AppDatabase
import com.example.fundraisinghome.data.UserRepository
import com.example.fundraisinghome.model.UserSingleton
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
    val errorMessageState = remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val userRepository = remember { UserRepository(database.userDao()) }

    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_big),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(vertical = 0.dp, horizontal = 12.dp)
            )
            // Input fields for username, password, and confirm password
            TextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Register button
            Button(onClick = {
                val username = usernameState.value
                val password = passwordState.value
                val confirmPassword = confirmPasswordState.value
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    errorMessageState.value = "Please fill in all fields"
                } else if (password == confirmPassword) {
                    coroutineScope.launch {
//                        // Retrieve ProductDao instance from your database
//                        val productDao = database.productDao()
//
//
//                        // Insert products into the database
//                        for (product in productList) {
//                            productDao.insertProduct(product)
//                        }
                    val registrationSuccessful = UserSingleton.registerUser(username, password, userRepository)

                    if (registrationSuccessful) {
                        navigateToHome()
                    } else {
                        // Show error message (e.g., username already exists)
                        errorMessageState.value = "username already exists"
                    }}
                } else {
                    // Show error message (e.g., passwords don't match)
                    errorMessageState.value = "Passwords don't match"
                }
            }) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Switch to Register button
            TextButton(onClick = navigateToLogin) {
                Text("Already have an account? Login here")
            }


        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(Alignment.BottomStart) // Align the Snackbar to the bottom left
        ) {
            errorMessageState.value?.let { errorMessage ->
                Snackbar(
                    modifier = Modifier.wrapContentSize(Alignment.BottomStart), // Wrap content and align to the bottom left
                    action = {
                        TextButton(onClick = { errorMessageState.value = null }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }
        }
    }
}