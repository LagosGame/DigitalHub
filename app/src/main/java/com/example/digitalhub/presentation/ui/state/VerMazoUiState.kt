package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo

data class VerMazoUiState(
    val mazo: Mazo? = null,
    val todasLasCartas: List<Carta> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)