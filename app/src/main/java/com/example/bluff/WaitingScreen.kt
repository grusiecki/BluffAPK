package com.example.bluff

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import model.User

@Composable
fun WaitingScreen() {
        var users by remember { mutableStateOf<List<User>>(emptyList()) }

    LaunchedEffect(Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("users")
//            .whereEqualTo("isWaiting", true)
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    // Obsługuj błędy
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    users = snapshot.toObjects(User::class.java)
                }
            }
    }
    getUsersFromFirestore{users}
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
            Text("Zalogowani użytkownicy:", color = Color.White, fontSize = 48.sp)
            if (users.isEmpty()) {
                Text("Brak aktywnych użytkowników.")
            } else {
                users.forEach { user ->
                    user.email?.let { Text(text = it) }
                }
            }

        }
    }
}
fun getUsersFromFirestore(onResult: (List<User>) -> Unit) {
    val db = Firebase.firestore
    db.collection("users")
        .get()
        .addOnSuccessListener { result ->
            val users = result.documents.mapNotNull { it.toObject(User::class.java) }
            Log.d("UserFetch", "Pobrano użytkowników: ${users.size}") // Logowanie liczby pobranych użytkowników
            onResult(users) // Zwróć listę użytkowników
        }
        .addOnFailureListener { e ->
            Log.e("UserFetch", "Błąd podczas pobierania użytkowników", e) // Logowanie błędów
        }
}