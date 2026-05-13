package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.AutentificacionRepository

class LoginUseCase(private val AutentifRepo : AutentificacionRepository)
{
    //Aqui haces logica, para cada cosa ACUERDATE//
    suspend operator fun invoke(username: String, password: String): Result<User> {

        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Username & password cannot be empty"))
        }

        if (password.length < 6) {
            return Result.failure(Exception("Password must have at least 6 characters"))
        }

        //Llamar al repositorio
        return AutentifRepo.login(username, password)
    }
}