package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Mazo

interface MazoRpository {
    suspend fun getMazos(): List<Mazo>
    suspend fun getMazoById(id: String): Mazo?
    suspend fun getMazosByUserId(userId: String): List<Mazo>
    suspend fun crearMazo(mazo: Mazo): Result<Unit>
    suspend fun actualizarMazo(mazo: Mazo): Result<Unit>
    suspend fun eliminarMazo(id: String): Result<Unit>
    suspend fun añadirCartaAMazo(
        userId: String,
        mazoId: String,
        cartaId: String,
        esCartaHuevo: Boolean,
        cantidad: Int = 1
    ): Result<Unit>

}