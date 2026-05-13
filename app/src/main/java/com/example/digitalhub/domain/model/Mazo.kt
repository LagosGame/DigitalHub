package com.example.digitalhub.domain.model

data class Mazo(
    val id: String= "",
    val nombre: String = "",
    val userId: String = "",
    val colores: List<ColorCarta> = emptyList(),
    val cartasNormales: Int = 0,
    val cartasHuevo: Int = 0,
    val portadaId: String? = null,
    val tags: List<String> = emptyList(),
    val cartas: List<CartaEnMazo> = emptyList(),
    val esFavorito : Boolean = false,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaModificacion: Long = System.currentTimeMillis(),

    //para detalles//
    val descripcion: String = "",
    val estrategias: List<Estrategia> = emptyList(),
    val cartasImportantes: List<String> = emptyList(),
    val estadisticas: Estadisticas = Estadisticas()

)
data class CartaEnMazo(
    val cartaId: String= "",
    val cantidad: Int = 0
)

data class Estrategia(
    val titulo: String= "",
    val cartasIds: List<String> = emptyList()
)
data class Estadisticas(
    val ataque: Int = 5,
    val defensa: Int = 5,
    val consistencia: Int = 5,
    val versatilidad: Int = 5,
    val recuperacion: Int = 5
)