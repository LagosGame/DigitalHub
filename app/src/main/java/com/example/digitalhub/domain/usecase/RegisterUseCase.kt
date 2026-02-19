package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.repository.AutentificacionRepository

class RegisterUseCase(
    private val autentificacionRepository: AutentificacionRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): AutentificaciónResultado{
        if (username.isBlank()) {
            return AutentificaciónResultado.Incorrecto("Username cannot be blank")
        }

        if (email.isBlank()) {
            return AutentificaciónResultado.Incorrecto("Email cannot be blank")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AutentificaciónResultado.Incorrecto("Invalid email")
        }

        if (password.isBlank()) {
            return AutentificaciónResultado.Incorrecto("Password cannot be blank")
        }

        if (password.length < 6) {
            return AutentificaciónResultado.Incorrecto("Password must have at least 6 characters")
        }

        if (password != confirmPassword) {
            return AutentificaciónResultado.Incorrecto("Passwords don't match")
        }

        return autentificacionRepository.registrar(username, email, password)
    }
}