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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import model.AuthViewModel
import model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
                        registerUser(email, password)
                        authViewModel.register(email, password)
                    } else {
                        authViewModel.login(email, password)
                    }
                    result?.let { errorMessage = it }
                    if (result == null) {
                        loginUser(email, password)
                        updateUserStatus(true)
                        navController.navigate("waitingScreen")
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



fun updateUserStatus(isWaiting: Boolean) {
    val user = Firebase.auth.currentUser
    user?.let {
        val userInfo = User(userId = it.uid, email = it.email ?: "", isWaiting = isWaiting)
        val db = Firebase.firestore
        db.collection("users").document(it.uid).set(userInfo)
            .addOnSuccessListener {
                // Udało się zaktualizować status
            }
            .addOnFailureListener { e ->
                // Obsługuj błąd
            }
    }
}
suspend fun registerUser(email: String, password: String): FirebaseUser? {
    val auth = FirebaseAuth.getInstance()
    return try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user // Zwracamy próbę uzyskania użytkownika z wyniku
    } catch (e: Exception) {
        e.printStackTrace()
        null // Zwracamy null w razie niepowodzenia
    }
}
suspend fun loginUser(email: String, password: String): FirebaseUser? {
    val auth = FirebaseAuth.getInstance()
    return try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user // Uzyskaj obiekt użytkownika z wyniku
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }}