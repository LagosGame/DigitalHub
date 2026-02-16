package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.repository.AutentificacionRepository

class LoginGoogleCase(
    private val AutentifRepo : AutentificacionRepository
) {
    suspend operator fun invoke(): AutentificaciónResultado{
        return AutentifRepo.loginGoogle()
    }
}