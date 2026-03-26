package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.repository.ComentarioRepository

class AgregarComentarioUseCase(
    private val repository: ComentarioRepository
) {
    suspend operator fun invoke(comentario: Comentario) {
        repository.agregarComentario(comentario)
    }
}