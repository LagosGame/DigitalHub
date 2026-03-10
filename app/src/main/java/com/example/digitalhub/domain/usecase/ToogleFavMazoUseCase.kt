package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.MazoRpository

class ToggleFavMazoUseCase(
    private val repository: MazoRpository
) {
    suspend operator fun invoke(mazoId: String) {
        val mazo = repository.getMazoById(mazoId) ?: return
        val mazoActualizado = mazo.copy(esFavorito = !mazo.esFavorito)
        repository.actualizarMazo(mazoActualizado)
    }
}