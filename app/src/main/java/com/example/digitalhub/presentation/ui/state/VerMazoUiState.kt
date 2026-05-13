package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.User

data class VerMazoUiState(
    val mazo: Mazo? = null,
    val usuario: User? = null,
    val todasLasCartas: List<Carta> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)