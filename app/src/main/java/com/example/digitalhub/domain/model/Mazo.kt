package com.example.digitalhub.domain.model

data class Mazo(
    val id: String,
    val nombre: String,
    val colores: List<ColorCarta>,
    val cartasNormales: Int = 0,
    val cartasHuevo: Int = 0,
    val portadaId: Int,
    val tags: List<String> = emptyList(),
    val cartas: List<CartaEnMazo> = emptyList(),

    //para detalles//
    val descripcion: String = "",
    val estrategias: List<Estrategia> = emptyList(),
    val cartasImportantes: List<String> = emptyList(),
    val estadisticas: Estadisticas = Estadisticas()
)
data class CartaEnMazo(
    val cartaId: String,
    val cantidad: Int
)

data class Estrategia(
    val titulo: String,
    val cartasIds: List<String>
)
data class Estadisticas(
    val ataque: Int = 5,
    val defensa: Int = 5,
    val consistencia: Int = 5,
    val versatilidad: Int = 5,
    val recuperacion: Int = 5
)