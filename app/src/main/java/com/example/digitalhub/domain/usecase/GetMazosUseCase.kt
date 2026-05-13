package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.MazoRpository

class GetMazosUseCase(
    private val mazoRepository: MazoRpository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend operator fun invoke(): List<Mazo> {
        val user = getCurrentUserUseCase() ?: return emptyList()
        return mazoRepository.getMazosByUserId(user.id)
    }
}