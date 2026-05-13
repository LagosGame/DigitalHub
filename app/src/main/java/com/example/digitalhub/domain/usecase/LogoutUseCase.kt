package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.repository.AutentificacionRepository

class LogoutUseCase(
    private val authRepository: AutentificacionRepository
) {
    suspend operator fun invoke(){
        authRepository.logout()
    }
}