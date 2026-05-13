package com.example.digitalhub.domain.model

import com.example.digitalhub.R

data class User(
    val id : String,
    val username : String,
    val email : String,
    val iconoId: Int = R.drawable.ic_launcher_foreground,
    val cumpleanos: String = "",
    val colorFavorito: ColorCarta? = null,
    val biografia: String = "",
    val mazosIds: List<String> = emptyList()
)
