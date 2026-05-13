package com.example.digitalhub.data.repository

import com.example.digitalhub.domain.model.CartaBiblioteca
import com.example.digitalhub.domain.repository.BibliotecaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BibliotecaRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : BibliotecaRepository {

    override suspend fun getCartasBiblioteca(userId: String): List<CartaBiblioteca> {
        return try {
            val snapshot = firestore
                .collection("bibliotecas")
                .document(userId)
                .collection("cartas")
                .get()
                .await()

            val cartas = snapshot.documents.mapNotNull { doc ->
                CartaBiblioteca(
                    userId = userId,
                    cartaId = doc.id,
                    cantidad = doc.getLong("cantidad")?.toInt() ?: 0,
                    esFavorita = doc.getBoolean("esFavorita") ?: false,
                    fechaAdquirida = doc.getLong("fechaAdquirida") ?: System.currentTimeMillis()
                )
            }
            cartas
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun addOrUpdateCarta(userId: String, cartaId: String, cantidad: Int) {
        try {
            val data = hashMapOf<String, Any>(
                "cantidad" to cantidad,
                "fechaAdquirida" to System.currentTimeMillis()
            )

            firestore
                .collection("bibliotecas")
                .document(userId)
                .collection("cartas")
                .document(cartaId)
                .set(data, com.google.firebase.firestore.SetOptions.merge())
                .await()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            throw e
        }
    }

    override suspend fun toggleFavorita(userId: String, cartaId: String, esFavorita: Boolean) {
        try {
            firestore
                .collection("bibliotecas")
                .document(userId)
                .collection("cartas")
                .document(cartaId)
                .update("esFavorita", esFavorita)
                .await()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            throw e
        }
    }

    override suspend fun incrementarCantidad(userId: String, cartaId: String) {
        try {
            val docRef = firestore
                .collection("bibliotecas")
                .document(userId)
                .collection("cartas")
                .document(cartaId)

            val doc = docRef.get().await()
            val cantidadActual = doc.getLong("cantidad")?.toInt() ?: 0
            val nuevaCantidad = cantidadActual + 1

            docRef.update("cantidad", nuevaCantidad).await()
        } catch (e: Exception) {
            addOrUpdateCarta(userId, cartaId, 1)
        }
    }

    override suspend fun decrementarCantidad(userId: String, cartaId: String) {
        try {
            val docRef = firestore
                .collection("bibliotecas")
                .document(userId)
                .collection("cartas")
                .document(cartaId)

            val doc = docRef.get().await()
            val cantidadActual = doc.getLong("cantidad")?.toInt() ?: 0
            val nuevaCantidad = (cantidadActual - 1).coerceAtLeast(0)

            if (nuevaCantidad == 0) {
                docRef.delete().await()
            } else {
                docRef.update("cantidad", nuevaCantidad).await()
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}