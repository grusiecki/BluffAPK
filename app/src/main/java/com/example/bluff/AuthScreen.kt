package com.example.bluff

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.google.firebase.firestore.FirebaseFirestore
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
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, Color.Black, CircleShape)
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.scale(1.5f)
            )
        }
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
                    if (isRegistering) {
                        // Rejestracja
                        val result = registerUserAndSaveToFirestore(email, password)
                        if (result != null) { // Rejestracja powiodła się
                            errorMessage = "Rejestracja przebiegła pomyślnie"
                        } else {
                            errorMessage = "Rejestracja nie powiodła się. Sprawdź dane."
                        }
                    } else {
                        // Logowanie
                        val result = loginUser(email, password)
                        if (result != null) { // Logowanie powiodło się
                            updateUserStatus(true) // Ustaw status oczekiwania

                            navController.navigate("waitingScreen") // Przekieruj do waiting screen
                        } else {
                            errorMessage = "Logowanie nie powiodło się. Sprawdź dane."
                        }
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
        db.collection("users").document(it.uid).update("isWaiting", isWaiting)
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
fun saveUserToFirestore(userId: String, email: String, isWaiting: Boolean) {
    val db = Firebase.firestore
    val userData = User(userId = userId, email = email, isWaiting = isWaiting)

    Log.d("FirestoreSave", "Saving user: ID = $userId, Email = $email")

    db.collection("users").document(userId).set(userData)
        .addOnSuccessListener {
            Log.d("FirestoreSave", "User saved: $userId")
        }
        .addOnFailureListener { e ->
            Log.e("FirestoreSave", "Saved unsuccessfull: ", e)
        }
}
suspend fun registerUserAndSaveToFirestore(email: String, password: String): User? {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    return try {
        // Rejestracja użytkownika
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = result.user?.uid ?: return null // UID użytkownika

        // Tworzenie obiektu użytkownika z isWaiting = true
        val userData = User(userId = userId, email = email, isWaiting = false)

        // Zapisywanie danych w Firestore
        db.collection("users").document(userId).set(userData).await()

        // Zwracanie zapisanego użytkownika
        userData
    } catch (e: Exception) {
        e.printStackTrace() // Obsłuż błąd
        null // Zwróć null w przypadku błędu
    }
}