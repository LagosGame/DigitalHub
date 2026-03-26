package com.example.digitalhub.domain.repository

import com.example.digitalhub.data.local.FakeComentarioDataSource
import com.example.digitalhub.domain.model.Comentario

class ComentarioRepositoryImpl(
    private val dataSource: FakeComentarioDataSource
): ComentarioRepository {
    override suspend fun getComentariosPorMazo(mazoId: String): List<Comentario> {
        return dataSource.getComentariosPorMazo(mazoId)
    }

    override suspend fun agregarComentario(comentario: Comentario) {
        dataSource.agregarComentario(comentario)
    }

    override suspend fun toggleLike(comentarioId: String,usuarioId:String) {
        dataSource.toggleLike(comentarioId,usuarioId)
    }
}