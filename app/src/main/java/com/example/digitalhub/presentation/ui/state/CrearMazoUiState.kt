package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.CartaEnMazo

data class CrearMazoUiState(
    val nombreMazo: String = "",
    val cartasNormales: List<CartaEnMazo> = emptyList(),
    val cartasHuevo: List<CartaEnMazo> = emptyList(),
    val totalNormales: Int = 0,
    val totalHuevo: Int = 0,
    val cartaIdPortada : String? ="",
    val errorMessage: String? = null,

    // Bibliotecs//

    val cartasBiblioteca: List<Carta> = emptyList(),
    val isLoadingBiblioteca : Boolean = false
)
