package com.example.digitalhub.data.repository

import com.example.digitalhub.domain.model.*
import com.example.digitalhub.domain.repository.MazoRpository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MazoRepositoryFirestoreImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : MazoRpository {

    override suspend fun getMazos(): List<Mazo> {
        return try {
            val snapshot = firestore.collection("mazos").get().await()

            val mazos = snapshot.documents.mapNotNull { doc ->
                doc.data?.let { parseMazo(doc.id, it) }
            }
            mazos
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    override suspend fun getMazosByUserId(userId: String): List<Mazo> {
        return try {
            val snapshot = firestore.collection("mazos")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val mazos = snapshot.documents.mapNotNull { doc ->
                doc.data?.let { parseMazo(doc.id, it) }
            }
            mazos
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getMazoById(id: String): Mazo? {
        return try {
            val doc = firestore.collection("mazos").document(id).get().await()

            if (doc.exists() && doc.data != null) {
                val mazo = parseMazo(doc.id, doc.data!!)
                mazo
            } else null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    override suspend fun crearMazo(mazo: Mazo): Result<Unit> {
        return try {
            val mazoId = firestore.collection("mazos").document().id

            val mazoConId = mazo.copy(id = mazoId)
            val mazoMap = mazoToMap(mazoConId)

            firestore.collection("mazos")
                .document(mazoId)
                .set(mazoMap)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            println("Error creating deck: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun actualizarMazo(mazo: Mazo): Result<Unit> {
       return try {
            val data = mazoToMap(mazo).toMutableMap()
            data["fechaModificacion"] = System.currentTimeMillis()

            firestore.collection("mazos")
                .document(mazo.id)
                .set(data)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            println("Error: ${e.message}")
           e.printStackTrace()
           Result.failure(e)
       }
    }

    override suspend fun eliminarMazo(id: String): Result<Unit> {
        return try {

            firestore.collection("mazos")
                .document(id)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun añadirCartaAMazo(
        userId: String,
        mazoId: String,
        cartaId: String,
        esCartaHuevo: Boolean,
        cantidad: Int
    ): Result<Unit> {
        return try {
            val mazoDoc = firestore
                .collection("mazos")
                .document(mazoId)
                .get()
                .await()

            if (!mazoDoc.exists()) {
                return Result.failure(Exception("Deck not found"))
            }

            val mazoData = mazoDoc.data ?: return Result.failure(Exception("Deck data not available"))

            if (mazoData["userId"] as? String != userId) {
                return Result.failure(Exception("You don't have permission to change this deck"))
            }

            val mazo = parseMazo(mazoId, mazoData)

            if (!esCartaHuevo && mazo.cartasNormales >= 50) {
                return Result.failure(Exception("Deck already has 50 cards"))
            }
            if (esCartaHuevo && mazo.cartasHuevo >= 5) {
                return Result.failure(Exception("Deck already has maximum Digi-Eggs (5)"))
            }

            val cartaExistenteEnMazo = mazo.cartas.find { it.cartaId == cartaId }
            if (cartaExistenteEnMazo != null) {
                val maxCopiasPermitidas = 4
                if (cartaExistenteEnMazo.cantidad >= maxCopiasPermitidas) {
                    return Result.failure(Exception("Maximum copies (${maxCopiasPermitidas}) reached for this card"))
                }
            }

            val cartasActuales = mazo.cartas.toMutableList()
            val cartasActualizadas = if (cartaExistenteEnMazo != null) {
                val nuevaCantidad = cartaExistenteEnMazo.cantidad + cantidad

                cartasActuales.map {
                    if (it.cartaId == cartaId) {
                        it.copy(cantidad = nuevaCantidad)
                    } else {
                        it
                    }
                }
            } else {
                cartasActuales + CartaEnMazo(cartaId, cantidad)
            }

            val nuevoMazo = mazo.copy(
                cartas = cartasActualizadas,
                cartasNormales = if (esCartaHuevo) mazo.cartasNormales else mazo.cartasNormales + cantidad,
                cartasHuevo = if (esCartaHuevo) mazo.cartasHuevo + cantidad else mazo.cartasHuevo,
                fechaModificacion = System.currentTimeMillis()
            )

            val mazoMap = mazoToMap(nuevoMazo)
            firestore.collection("mazos")
                .document(mazoId)
                .set(mazoMap)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
    private fun mazoToMap(mazo: Mazo): Map<String, Any?> {
        return hashMapOf(
            "nombre" to mazo.nombre,
            "userId" to mazo.userId,
            "colores" to mazo.colores.map { it.name },
            "cartasNormales" to mazo.cartasNormales,
            "cartasHuevo" to mazo.cartasHuevo,
            "portadaId" to mazo.portadaId,
            "tags" to mazo.tags,
            "cartas" to mazo.cartas.map { cartaMazo ->
                mapOf(
                    "cartaId" to cartaMazo.cartaId,
                    "cantidad" to cartaMazo.cantidad
                )
            },
            "esFavorito" to mazo.esFavorito,
            "fechaCreacion" to mazo.fechaCreacion,
            "fechaModificacion" to mazo.fechaModificacion,
            "descripcion" to mazo.descripcion,
            "estrategias" to mazo.estrategias.map { estrategiaToMap(it) },
            "cartasImportantes" to mazo.cartasImportantes,
            "estadisticas" to estadisticasToMap(mazo.estadisticas)
        )
    }

    private fun parseMazo(id: String, data: Map<String, Any>): Mazo {
        return Mazo(
            id = id,
            nombre = data["nombre"] as? String ?: "",
            userId = data["userId"] as? String ?: "",
            colores = (data["colores"] as? List<*>)?.mapNotNull {
                try { ColorCarta.valueOf(it as String) } catch (e: Exception) { null }
            } ?: emptyList(),
            cartasNormales = (data["cartasNormales"] as? Long)?.toInt() ?: 0,
            cartasHuevo = (data["cartasHuevo"] as? Long)?.toInt() ?: 0,
            portadaId = data["portadaId"] as? String,
            tags = (data["tags"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            cartas = (data["cartas"] as? List<*>)?.mapNotNull { parseCartaEnMazo(it) } ?: emptyList(),
            esFavorito = data["esFavorito"] as? Boolean ?: false,
            fechaCreacion = (data["fechaCreacion"] as? Long) ?: System.currentTimeMillis(),
            fechaModificacion = (data["fechaModificacion"] as? Long) ?: System.currentTimeMillis(),
            descripcion = data["descripcion"] as? String ?: "",
            estrategias = (data["estrategias"] as? List<*>)?.mapNotNull { parseEstrategia(it) } ?: emptyList(),
            cartasImportantes = (data["cartasImportantes"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            estadisticas = parseEstadisticas(data["estadisticas"])
        )
    }

    private fun parseCartaEnMazo(data: Any?): CartaEnMazo? {
        val map = data as? Map<*, *> ?: return null
        return CartaEnMazo(
            cartaId = map["cartaId"] as? String ?: return null,
            cantidad = (map["cantidad"] as? Long)?.toInt() ?: 0
        )
    }

    private fun estrategiaToMap(estrategia: Estrategia): Map<String, Any> {
        return mapOf(
            "titulo" to estrategia.titulo,
            "cartasIds" to estrategia.cartasIds
        )
    }

    private fun parseEstrategia(data: Any?): Estrategia? {
        val map = data as? Map<*, *> ?: return null
        return Estrategia(
            titulo = map["titulo"] as? String ?: "",
            cartasIds = (map["cartasIds"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
        )
    }

    private fun estadisticasToMap(stats: Estadisticas): Map<String, Int> {
        return mapOf(
            "ataque" to stats.ataque,
            "defensa" to stats.defensa,
            "consistencia" to stats.consistencia,
            "versatilidad" to stats.versatilidad,
            "recuperacion" to stats.recuperacion
        )
    }

    private fun parseEstadisticas(data: Any?): Estadisticas {
        val map = data as? Map<*, *> ?: return Estadisticas()
        return Estadisticas(
            ataque = (map["ataque"] as? Long)?.toInt() ?: 5,
            defensa = (map["defensa"] as? Long)?.toInt() ?: 5,
            consistencia = (map["consistencia"] as? Long)?.toInt() ?: 5,
            versatilidad = (map["versatilidad"] as? Long)?.toInt() ?: 5,
            recuperacion = (map["recuperacion"] as? Long)?.toInt() ?: 5
        )
    }
}