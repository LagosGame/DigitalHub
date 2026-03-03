package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.CartaRepository
import com.example.digitalhub.domain.repository.MazoRpository

class GetMazoByIdUseCase(
    private val repository: MazoRpository
) {
    suspend operator fun invoke(id: String): Mazo?{
        return repository.getMazoById(id)
    }
}