package com.example.digitalhub.domain.repository

import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.model.Mazo

class MazoRepositoryImpl(
    private val fakeMazoDataSource: FakeMazoDataSource
): MazoRpository {
    override suspend fun getMazos(): List<Mazo> {
        return fakeMazoDataSource.getMazos()
    }

    override suspend fun getMazoById(id: String): Mazo? {
        return fakeMazoDataSource.getMazoById(id)
    }

    override suspend fun crearMazo(mazo: Mazo) {
        fakeMazoDataSource.crearMazo(mazo)
    }

    override suspend fun actualizarMazo(mazo: Mazo) {
        fakeMazoDataSource.actualizarMazo(mazo)
    }

    override suspend fun eliminarMazo(id: String) {
        fakeMazoDataSource.eliminarMazo(id)
    }
}