package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.repository.ComentarioRepository

class GetComentariosUseCase(
    private val repository: ComentarioRepository
) {
    suspend operator fun invoke(mazoId: String): List<Comentario> {
        return repository.getComentariosPorMazo(mazoId)
    }
    suspend fun getRespuestas(comentarioPadreId: String): List<Comentario> {
        return repository.getRespuestas(comentarioPadreId)
    }
}