package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta

data class CartaDetalleUiState(
    val carta : Carta? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
    )
