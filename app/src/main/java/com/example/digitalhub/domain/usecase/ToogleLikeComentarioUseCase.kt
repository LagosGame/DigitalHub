package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.ComentarioRepository

class ToggleLikeComentarioUseCase(
    private val repository: ComentarioRepository
) {
    suspend operator fun invoke(comentarioId: String,usuarioId: String) {
        repository.toggleLike(comentarioId,usuarioId)
    }
}