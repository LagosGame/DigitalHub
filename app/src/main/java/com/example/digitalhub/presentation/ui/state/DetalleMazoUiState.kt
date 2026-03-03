package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Estrategia
import com.example.digitalhub.domain.model.Mazo

data class DetalleMazoUiState(
    val mazo : Mazo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    //editar
    val descripcion: String = "",
    val estrategias: List<Estrategia> = emptyList(),
    val cartasImportantes: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val estadisticas: EstadisticasEdit = EstadisticasEdit(),

    //(para mostrar las cartas)
    val todasLasCartas: List<Carta> = emptyList(),

    //todos los dialogos
    val mostrarDialogoDescripcion: Boolean = false,
    val mostrarDialogoEstrategia: Boolean = false,
    val mostrarDialogoCartasImportantes: Boolean = false,
    val mostrarDialogoTags: Boolean = false,
    val mostrarDialogoEstadisticas: Boolean = false
)
data class EstadisticasEdit(
    val ataque: Int = 5,
    val defensa: Int = 5,
    val consistencia: Int = 5,
    val versatilidad: Int = 5,
    val recuperacion: Int = 5
)