package com.example.digitalhub.data.local

import com.example.digitalhub.R
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.User

class FakeUserDataSource {

    private val usuarios = mutableListOf(
        User(
            id = "user1",
            username = "BananaTCG",
            email = "banana@example.com",
            iconoId = R.drawable.ic_launcher_foreground,
            cumpleanos = "18/07",
            colorFavorito = ColorCarta.BLUE,
            biografia = "Me encantan las cartas azules pero nunca hago mazos de ese color",
            mazosIds = listOf("1")
        ),
        User(
            id = "user2",
            username = "PeraUnMomento",
            email = "pera@example.com",
            iconoId = R.drawable.ic_launcher_foreground,
            cumpleanos = "05/03",
            colorFavorito = ColorCarta.GREEN,
            biografia = "Coleccionista desde 2020. Me gusta jugar agresivo.",
            mazosIds = listOf("2")
        ),
        User(
            id = "current_user",
            username = "TúMismo",
            email = "tu@example.com",
            iconoId = R.drawable.ic_launcher_foreground,
            cumpleanos = "01/01",
            colorFavorito = ColorCarta.RED,
            biografia = "Jugador casual de Digimon TCG",
            mazosIds = listOf("3")
        )
    )

    fun getUserById(userId: String): User? {
        return usuarios.find { it.id == userId }
    }

    fun updateUser(user: User) {
        val index = usuarios.indexOfFirst { it.id == user.id }
        if (index != -1) {
            usuarios[index] = user
        }
    }
}