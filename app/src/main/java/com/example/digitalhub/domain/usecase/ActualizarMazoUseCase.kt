package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.MazoRpository

class ActualizarMazoUseCase(
    private val repository: MazoRpository
) {
    suspend operator fun invoke(mazo: Mazo) {
        repository.actualizarMazo(mazo)
    }
}