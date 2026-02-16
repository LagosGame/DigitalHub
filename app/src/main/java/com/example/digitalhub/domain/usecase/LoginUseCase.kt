package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.repository.AutentificacionRepository

class LoginUseCase(private val AutentifRepo : AutentificacionRepository)
{
    //Aqui haces logica, para cada cosa ACUERDATE//
    suspend operator fun invoke(username: String, password: String): AutentificaciónResultado{
        if (username.isBlank() || password.isBlank()){
            return AutentificaciónResultado.Incorrecto("User & password cannot be empty.")
        }
        if (password.length<6){
            return AutentificaciónResultado.Incorrecto("Password must have at least 6 characters.")
        }
        return AutentifRepo.login(username,password)
    }
}