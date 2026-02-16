package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.AutentificaciónResultado

interface AutentificacionRepository {

    suspend fun login(username: String, password : String): AutentificaciónResultado
    suspend fun loginGoogle(): AutentificaciónResultado
    suspend fun registrar(username: String,email:String,password: String): AutentificaciónResultado
}