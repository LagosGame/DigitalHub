package com.example.digitalhub.domain.model

data class Mazo(
    val id: String,
    val nombre: String,
    val colores: List<ColorCarta>,
    val cartasNormales: Int = 0,
    val cartasHuevo: Int = 0,
    val portadaId: Int,
    val tags: List<String> = emptyList(),
    val cartas: List<CartaEnMazo> = emptyList()
)
data class CartaEnMazo(
    val cartaId: String,
    val cantidad: Int
)