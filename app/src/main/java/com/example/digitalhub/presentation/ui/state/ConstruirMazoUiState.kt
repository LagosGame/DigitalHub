package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Mazo

data class ConstruirMazoUiState(
    val mazos : List<Mazo> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage : String? = null
)
