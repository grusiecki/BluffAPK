package com.example.bluff

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import model.AuthViewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isRegistering by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Hasło") },
            visualTransformation = PasswordVisualTransformation()
        )
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            errorMessage = null // Reset error message
            scope.launch {
                val result = if (isRegistering) {
                    authViewModel.register(email, password)
                } else {
                    authViewModel.login(email, password)
                }
                result?.let { errorMessage = it }
            }
        }) {
            Text(if (isRegistering) "Register" else "Log in")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { isRegistering = !isRegistering }) {
            Text(if (isRegistering) "Have account yet? Log in" else "Do not have account? Register")
        }
    }
}