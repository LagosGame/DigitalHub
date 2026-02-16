package com.example.digitalhub.data.local

import com.example.digitalhub.data.remote.dto.LoginResponseDto
import com.example.digitalhub.data.remote.dto.UserDto
import kotlinx.coroutines.delay

class FakeAuthDataSource {

    private val usuariosRegistrado = mutableListOf(
        UserDto(
            id = "1",
            username = "demo",
            email = "demo@gmail.com",
            creadoEn = "2026-02-16"
        ),
        UserDto(
            id = "2",
            username = "admin",
            email = "admin@gmail.com",
            creadoEn = "2026-02-15"
        )
    )

    private val credencialesVálidas = mutableMapOf(
        "demo" to "123456",
        "admin" to "admin123"
    )

    suspend fun login(username: String, password: String): LoginResponseDto{
        delay(800)
        val isValid = credencialesVálidas[username] == password

        return if (isValid){
            val user = usuariosRegistrado.find { it.username == username}
            LoginResponseDto(
                exito = true,
                user = user,
                token = "Token_fake_${System.currentTimeMillis()}",
                mensaje = "Successful login"
            )
        }
        else{
            LoginResponseDto(
                exito = false,
                user = null,
                token = null,
                mensaje = "Wrong username or password"
            )
        }
    }

    suspend fun loginWithGoogle(): LoginResponseDto{
        delay(1000)

        val googleUser = UserDto(
            id = "google_${System.currentTimeMillis()}",
            username = "Usuario Google",
            email = "usuariogoogle@gmail.com",
            creadoEn = System.currentTimeMillis().toString()
        )
        return LoginResponseDto(
            exito = true,
            user = googleUser,
            token = "google_token_${System.currentTimeMillis()}",
            mensaje = "Successful Google login"
        )
    }

    suspend fun register(username: String,email: String, password: String): LoginResponseDto{
        delay(800)

        val exists = usuariosRegistrado.any{
            it.username.equals(username,ignoreCase = true) ||
                    it.email.equals(email,ignoreCase = true)
        }
        return if (exists){
            LoginResponseDto(
                exito = false,
                user = null,
                token = null,
                mensaje = "The username or email already exists"
            )
        }
        else
        {
            val nuevoUser = UserDto(
                id = System.currentTimeMillis().toString(),
                username = username,
                email = email,
                creadoEn = System.currentTimeMillis().toString()
            )

            usuariosRegistrado.add(nuevoUser)
            credencialesVálidas[username] = password

            LoginResponseDto(
                exito = true,
                user = nuevoUser,
                token = "token_${System.currentTimeMillis()}",
                mensaje = "Registration complete"
            )
        }
    }

}