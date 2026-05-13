package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.User

data class ListaMazosUiState(
    val mazos: List<Mazo> = emptyList(),
    val mazosPropios: List<Mazo> = emptyList(),
    val mazosOtros: List<Mazo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val usuarios: Map<String, User> = emptyMap(),
    val busqueda: String = "",
    val todasLasCartas: List<Carta> = emptyList()
)
