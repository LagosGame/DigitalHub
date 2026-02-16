package com.example.digitalhub.domain.model

sealed class AutentificaciónResultado{
    data class Correcto(val user: User): AutentificaciónResultado()
    data class Incorrecto(val mensaje : String) : AutentificaciónResultado()
}