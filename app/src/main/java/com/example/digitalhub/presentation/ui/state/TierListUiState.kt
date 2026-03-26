package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Arquetipo

data class TierListUiState(
    val arquetipos: List<Arquetipo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
