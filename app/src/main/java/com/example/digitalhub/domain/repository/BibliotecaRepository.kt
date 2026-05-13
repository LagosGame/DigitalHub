package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.CartaBiblioteca

interface BibliotecaRepository {
    suspend fun getCartasBiblioteca(userId: String): List<CartaBiblioteca>
    suspend fun addOrUpdateCarta(userId: String, cartaId: String, cantidad: Int)
    suspend fun toggleFavorita(userId: String, cartaId: String, esFavorita: Boolean)
    suspend fun incrementarCantidad(userId: String, cartaId: String)
    suspend fun decrementarCantidad(userId: String, cartaId: String)
}