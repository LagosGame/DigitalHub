package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.repository.CartaRepository

class GetCartaByIdUseCase(
    private val repository: CartaRepository
) {
    suspend operator fun invoke(id: String): Carta?{
        return repository.getCartaById(id)
    }
}