package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Arquetipo
import com.example.digitalhub.domain.repository.ArquetipoRepository

class GetArquetiposUseCase(
    private val repository: ArquetipoRepository
) {
    suspend operator fun invoke(): List<Arquetipo> {
        return repository.getArquetipos()
    }
}