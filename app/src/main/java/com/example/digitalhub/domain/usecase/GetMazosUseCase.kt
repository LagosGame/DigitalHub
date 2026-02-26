package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.repository.MazoRpository

class GetMazosUseCase(
    private val repository : MazoRpository
) {
    suspend operator fun invoke() : List<Mazo>{
      return  repository.getMazos()
    }
}