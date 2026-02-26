package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Mazo

interface MazoRpository {
    suspend fun getMazos(): List<Mazo>
    suspend fun getMazoById(id: String): Mazo?
    suspend fun crearMazo(mazo: Mazo)
    suspend fun actualizarMazo(mazo: Mazo)
    suspend fun eliminarMazo(id: String)
}