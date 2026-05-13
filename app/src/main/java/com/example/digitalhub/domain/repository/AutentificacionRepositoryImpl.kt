package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AutentificacionRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
): AutentificacionRepository {

    override suspend fun login(username: String, password: String): Result<User> {
        return try {

            val usersQuery = firestore.collection("users")
                .whereEqualTo("username", username)
                .limit(1)
                .get()
                .await()

            if (usersQuery.isEmpty) {
                return Result.failure(Exception("User not found"))
            }

            val userDoc = usersQuery.documents.first()
            val email = userDoc.getString("email")
                ?: return Result.failure(Exception("Email not found"))


            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID not found")

            val user = User(
                id = userId,
                username = userDoc.getString("username") ?: "User",
                email = email,
                iconoId = userDoc.getLong("iconoId")?.toInt()
                    ?: com.example.digitalhub.R.drawable.ic_launcher_foreground,
                cumpleanos = userDoc.getString("cumpleanos") ?: "",
                colorFavorito = null,
                biografia = userDoc.getString("biografia") ?: "",
                mazosIds = (userDoc.get("mazosIds") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
            )
            Result.success(user)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Wrong email or password"))
        } catch (e: Exception) {
            Result.failure(Exception("Error on login: ${e.message}"))
        }

    }



    override suspend fun loginGoogle(): AutentificaciónResultado {
        TODO("Not yet implemented")
    }

    override suspend fun registrar(
    email: String,
    password: String,
    username: String
    ): Result<User> {
        return try {
            //Crear usuario en Firebase

            val usernameQuery = firestore.collection("users")
                .whereEqualTo("username", username)
                .limit(1)
                .get()
                .await()

            if (!usernameQuery.isEmpty) {
                return Result.failure(Exception("Username already in use"))
            }


            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("User ID not found")
            val userId = firebaseUser.uid


            //Crear documento en firestore
            val userData = hashMapOf(
                "id" to userId,
                "username" to username,
                "email" to email,
                "iconoId" to com.example.digitalhub.R.drawable.ic_launcher_foreground,
                "cumpleanos" to "",
                "colorFavorito" to null,
                "biografia" to "",
                "mazosIds" to emptyList<String>()
            )

            firestore.collection("users")
                .document(userId)
                .set(userData)
                .await()

            val user = User(
                id = userId,
                username = username,
                email = email,
                iconoId = com.example.digitalhub.R.drawable.ic_launcher_foreground,
                cumpleanos = "",
                colorFavorito = null,
                biografia = "",
                mazosIds = emptyList()
            )

            Result.success(user)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Password must be at least 6 characters"))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("This email is already registered"))
        } catch (e: Exception) {
            Result.failure(Exception("Registration error: ${e.message}"))
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null

        return User(
            id = firebaseUser.uid,
            username = firebaseUser.displayName ?: "User",
            email = firebaseUser.email ?: "",
            iconoId = com.example.digitalhub.R.drawable.ic_launcher_foreground
        )
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            val user = auth.currentUser
            if (user == null) {
                return Result.failure(Exception("No user logged in"))
            }

            user.sendEmailVerification().await()
            println("Verification email sent")
            Result.success(Unit)
        } catch (e: Exception) {
            println("Error sending verification email: ${e.message}")
            Result.failure(e)
        }
    }

}