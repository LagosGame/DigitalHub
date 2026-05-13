package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.MazoRpository

class CrearMazoUseCase(
    private val repository: MazoRpository
) {
    suspend operator fun invoke(mazo: Mazo): Result<Unit> {
        return try {
            repository.crearMazo(mazo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}