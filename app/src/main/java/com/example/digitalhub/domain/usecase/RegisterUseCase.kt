package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.AutentificacionRepository

class RegisterUseCase(
    private val autentificacionRepository: AutentificacionRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User>{
        if (username.isBlank()) {
            return Result.failure(Exception("Username cannot be blank"))
        }

        if (username.length < 3) {
            return Result.failure(Exception("Username must have at least 3 characters"))
        }

        if (email.isBlank()) {
            return Result.failure(Exception("Email cannot be blank"))
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(Exception("Invalid email"))
        }

        if (password.isBlank()) {
            return Result.failure(Exception("Password cannot be blank"))
        }

        if (password.length < 6) {
            return Result.failure(Exception("Password must have at least 6 characters"))
        }

        if (password != confirmPassword) {
            return Result.failure(Exception("Passwords don't match"))
        }
        
        return autentificacionRepository.registrar(email, password, username)
    }
}