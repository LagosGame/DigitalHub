package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo

data class ListaMazosUiState(
    val mazos: List<Mazo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val busqueda: String = "",
    val todasLasCartas: List<Carta>? = null
)
