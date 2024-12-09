package com.example.bluff

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import model.AuthViewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isRegistering by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    Box(

        modifier = Modifier
            .fillMaxSize()
        //  .padding(paddingValues)

    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
                Box(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = errorMessage!!,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W900,
                                modifier = Modifier.padding(2.dp)
                    )
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W900
                    )
                }
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
                    if (result == null) {
                        navController.navigate("waiting_screen")
                    } else {
                        errorMessage = result
                    }
                }
            }) {
                Text(if (isRegistering) "Register" else "Log in")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { isRegistering = !isRegistering }) {
                Text(if (isRegistering) "Have account yet? Log in" else "Do not have account? Register", fontSize = 24.sp, color = Color.White )
            }
        }
    }
}