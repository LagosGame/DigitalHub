package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.MazoRpository

class GetAllMazosUseCase(
    private val mazoRpository: MazoRpository
) {
    suspend operator fun invoke(): List<Mazo>{
        return mazoRpository.getMazos()
    }
}