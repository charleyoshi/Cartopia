package com.example.fundraisinghome

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fundraisinghome.model.UserSingleton


@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {
    // Local state to hold username and password values
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorMessageState = remember { mutableStateOf<String?>(null) }

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

            // Input fields for username and password
            TextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = passwordState.value, // use state for actual values
                onValueChange = { passwordState.value = it },
                label = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Login button
            Button(onClick = {
                val loginSuccessful = UserSingleton.loginUser(usernameState.value, passwordState.value)
                if (!loginSuccessful) {
                    errorMessageState.value = "Invalid username or password"
                } else {
                    // Navigate to home screen or show success message
                    navigateToHome()
                }
            }) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Switch to Register button
            TextButton(onClick = navigateToRegister) {
                Text("Don't have an account? Register here")
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
