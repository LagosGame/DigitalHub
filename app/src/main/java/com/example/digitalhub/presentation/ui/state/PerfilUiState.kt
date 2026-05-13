package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.User

data class PerfilUiState(
    val usuario: User? = null,
    val mazosPropios: List<Mazo> = emptyList(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val todasLasCartas: List<Carta> = emptyList()
)
