package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.repository.CartaRepository

class GetCartasUseCase(
    private val repository: CartaRepository
) {
    suspend operator fun invoke(): List<Carta>{
        return repository.getCartas()
    }
}