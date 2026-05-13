package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Comentario

interface ComentarioRepository {
    suspend fun getComentariosPorMazo(mazoId: String): List<Comentario>
    suspend fun agregarComentario(comentario: Comentario)
    suspend fun toggleLike(comentarioId: String, usuarioId: String)
    suspend fun getRespuestas(comentarioPadreId: String): List<Comentario>
}