package com.example.digitalhub.data.repository

import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.ComentarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ComentarioRepositoryImpl : ComentarioRepository {
    private val db = FirebaseFirestore.getInstance()
    private val comentariosCollection = db.collection("comentarios")

    override suspend fun getComentariosPorMazo(mazoId: String): List<Comentario> {
        return try {

            val snapshot = comentariosCollection
                .whereEqualTo("mazoId", mazoId)
                .whereEqualTo("comentarioPadreId", null)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()


            val comentarios = snapshot.documents.mapNotNull { doc ->
                parseComentario(doc.data ?: return@mapNotNull null)
            }

            comentarios.forEach {
                println("  - ${it.autor.username}: ${it.contenido}")
            }

            comentarios
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun agregarComentario(comentario: Comentario) {
        try {
            val comentarioMap = mapOf(
                "id" to comentario.id,
                "mazoId" to comentario.mazoId,
                "autorId" to comentario.autorId,
                "contenido" to comentario.contenido,
                "likes" to comentario.likes,
                "timestamp" to comentario.timestamp,
                "comentarioPadreId" to comentario.comentarioPadreId,
                "usuariosQueDieronLike" to comentario.usuariosQueDieronLike
            )

            comentariosCollection.document(comentario.id).set(comentarioMap).await()
        } catch (e: Exception) {
            println("Error adding comment: ${e.message}")
            throw e
        }
    }

    override suspend fun toggleLike(comentarioId: String, userId: String) {
        try {
            val docRef = comentariosCollection.document(comentarioId)
            val doc = docRef.get().await()

            if (doc.exists()) {
                val usuariosLike = (doc.get("usuariosQueDieronLike") as? List<*>)
                    ?.filterIsInstance<String>()
                    ?.toMutableList() ?: mutableListOf()

                if (usuariosLike.contains(userId)) {
                    usuariosLike.remove(userId)
                } else {
                    usuariosLike.add(userId)
                }

                docRef.update(
                    mapOf(
                        "usuariosQueDieronLike" to usuariosLike,
                        "likes" to usuariosLike.size
                    )
                ).await()
            }
        } catch (e: Exception) {
            println("Error toggling like: ${e.message}")
            throw e
        }
    }

    override suspend fun getRespuestas(comentarioPadreId: String): List<Comentario> {
        return try {
            val snapshot = comentariosCollection
                .whereEqualTo("comentarioPadreId", comentarioPadreId)
               .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                parseComentario(doc.data ?: return@mapNotNull null)
            }
        } catch (e: Exception) {
            println("Error getting replies: ${e.message}")
            emptyList()
        }
    }

    private fun parseComentario(data: Map<String, Any>): Comentario {
        return Comentario(
            id = data["id"] as? String ?: "",
            mazoId = data["mazoId"] as? String ?: "",
            autorId = data["autorId"] as? String ?: "",
            autor = User(
                id = data["autorId"] as? String ?: "",
                username = "Loading...",
                email = ""
            ),
            contenido = data["contenido"] as? String ?: "",
            likes = (data["likes"] as? Long)?.toInt() ?: 0,
            timestamp = data["timestamp"] as? Long ?: 0L,
            comentarioPadreId = data["comentarioPadreId"] as? String?,
            usuariosQueDieronLike = (data["usuariosQueDieronLike"] as? List<*>)
                ?.filterIsInstance<String>() ?: emptyList(),
            respuestas = emptyList()
        )
    }
}