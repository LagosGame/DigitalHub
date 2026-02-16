package com.example.digitalhub.data.repository

import com.example.digitalhub.data.local.FakeAuthDataSource
import com.example.digitalhub.data.mapper.toDomain
import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.repository.AutentificacionRepository

class AtentifiRepositoryImpl
    (private val fakeAuthDataSource: FakeAuthDataSource)
    : AutentificacionRepository
{
    override suspend fun login(
        username: String,
        password: String
    ): AutentificaciónResultado {
        return try {
            val response = fakeAuthDataSource.login(username,password)

            if (response.exito && response.user != null){
                AutentificaciónResultado.Correcto(response.user.toDomain())
            }
            else{
                AutentificaciónResultado.Incorrecto(response.mensaje ?: "Unknown error")
            }
        }catch (e : Exception){
            AutentificaciónResultado.Incorrecto("Network error: ${e.message}")
        }
    }

    override suspend fun loginGoogle(): AutentificaciónResultado {
        return try {
            val response = fakeAuthDataSource.loginWithGoogle()

            if (response.exito && response.user != null){
                AutentificaciónResultado.Correcto(response.user.toDomain())
            }
            else{
                AutentificaciónResultado.Incorrecto(response.mensaje ?: "Google error")
            }
        }catch (e : Exception){
            AutentificaciónResultado.Incorrecto("Network error: ${e.message}")
        }
    }

    override suspend fun registrar(
        username: String,
        email: String,
        password: String
    ): AutentificaciónResultado {
        return try {
            val response = fakeAuthDataSource.register(username,email,password)

            if (response.exito && response.user != null){
                AutentificaciónResultado.Correcto(response.user.toDomain())
            }
            else{
                AutentificaciónResultado.Incorrecto(response.mensaje ?: "Unknown error at registration")
            }
        }catch (e : Exception){
            AutentificaciónResultado.Incorrecto("Network error: ${e.message}")
        }
    }

}