package com.example.digitalhub.domain.model

data class Arquetipo(
    val id: String,
    val nombre: String,
    val tier: Tier,
    val color: ColorCarta,
    val imagenId: String,
    val descripcion: String = ""
)
