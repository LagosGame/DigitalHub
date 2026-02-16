package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.AutentificaciónResultado

class AutentificacionRepositoryImpl: AutentificacionRepository {

    override suspend fun login(username: String, password: String): AutentificaciónResultado {
        TODO("Not yet implemented")
    }

    override suspend fun loginGoogle(): AutentificaciónResultado {
        TODO("Not yet implemented")
    }

    override suspend fun registrar(
        username: String,
        email: String,
        password: String
    ): AutentificaciónResultado {
        TODO("Not yet implemented")
    }

}