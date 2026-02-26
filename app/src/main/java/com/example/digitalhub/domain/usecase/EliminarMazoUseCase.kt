package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.MazoRpository

class EliminarMazoUseCase(
    private val repository : MazoRpository
) {
    suspend operator fun invoke(mazoId: String) {
        repository.eliminarMazo(mazoId)
    }
}