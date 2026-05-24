package com.example.digitalhub.data.repository

import com.example.digitalhub.R
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {
    override suspend fun getUserById(userId: String): User? {
        return try {
            val userDoc = firestore.collection("users").document(userId).get().await()

            if (!userDoc.exists()) return null

            User(
                id = userId,
                username = userDoc.getString("username") ?: "User",
                email = userDoc.getString("email") ?: "",
                iconoId = userDoc.getLong("iconoId")?.toInt()
                    ?: R.drawable.ic_launcher_foreground,
                cumpleanos = userDoc.getString("cumpleanos") ?: "",
                colorFavorito = userDoc.getString("colorFavorito")?.let {
                    try {
                        ColorCarta.valueOf(it)
                    } catch (e: Exception) {
                        null
                    }
                },
                biografia = userDoc.getString("biografia") ?: "",
                mazosIds = (userDoc.get("mazosIds") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateUser(user: User) {
        try {
            val userData = hashMapOf(
                "username" to user.username,
                "email" to user.email,
                "iconoId" to user.iconoId,
                "colorFavorito" to user.colorFavorito?.name,
                "cumpleanos" to user.cumpleanos,
                "biografia" to user.biografia,
                "mazosIds" to user.mazosIds
            )

            firestore.collection("users")
                .document(user.id)
                .update(userData as Map<String, Any>)
                .await()
        } catch (e: Exception) {
            throw Exception("Error updatinbg user: ${e.message}")
        }
    }
    override suspend fun isUsernameAvailable(username: String): Boolean {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()

            snapshot.isEmpty
        } catch (e: Exception) {
            println("Error : ${e.message}")
            false
        }
    }
}