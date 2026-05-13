package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.model.User

interface AutentificacionRepository {

    suspend fun login(username: String, password : String): Result<User>
    suspend fun loginGoogle(): AutentificaciónResultado
    suspend fun registrar(username: String,email:String,password: String): Result<User>
    suspend fun logout()
    fun getCurrentUser(): User?
    fun isUserLoggedIn(): Boolean
    suspend fun sendEmailVerification(): Result<Unit>
}