package com.example.digitalhub.domain.model

import androidx.annotation.DrawableRes

data class Noticia(
    @DrawableRes val imagen: Int,
    val url: String
)