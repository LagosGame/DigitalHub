package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo

data class CartaDetalleUiState(
    val carta : Carta? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mostrarDialogoSeleccionarMazo: Boolean = false,
    val mazosDisponibles: List<Mazo> = emptyList(),
    val todasLasCartas: List<Carta> = emptyList()
)
