package model

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun register(email: String, password: String): String? {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            null
        } catch (e: Exception) {
            e.message
        }
    }

    suspend fun login(email: String, password: String): String? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            null
        } catch (e: Exception) {
            e.message
        }
    }
}