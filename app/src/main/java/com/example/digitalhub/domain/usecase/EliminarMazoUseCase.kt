package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.MazoRpository

class EliminarMazoUseCase(
    private val repository : MazoRpository
) {
    suspend operator fun invoke(mazoId: String): Result<Unit> {
        return try {
            repository.eliminarMazo(mazoId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}