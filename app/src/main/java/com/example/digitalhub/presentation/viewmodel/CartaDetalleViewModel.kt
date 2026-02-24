package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.GetCartaByIdUseCase
import com.example.digitalhub.presentation.ui.state.CartaDetalleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartaDetalleViewModel(
    private val getCartaByIdUseCase: GetCartaByIdUseCase,
    private val cartaId : String
): ViewModel() {
    private val _uiState = MutableStateFlow(CartaDetalleUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCarta()
    }

    private fun fetchCarta() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val carta = getCartaByIdUseCase(cartaId)

                if (carta != null) {
                    _uiState.update {
                        it.copy(
                            carta = carta,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Card not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error loading card: ${e.message}"
                    )
                }
            }
        }
    }
    fun toggleFavorita() {
        _uiState.update { state ->
            state.carta?.let { carta ->
                state.copy(
                    carta = carta.copy(esFav = !carta.esFav)
                )
            } ?: state
        }
    }

    fun incrementarCantidad() {
        _uiState.update { state ->
            state.carta?.let { carta ->
                val nuevaCantidad = minOf(carta.cantidadEnBiblioteca + 1, 4)
                state.copy(
                    carta = carta.copy(cantidadEnBiblioteca = nuevaCantidad)
                )
            } ?: state
        }
    }

    fun decrementarCantidad() {
        _uiState.update { state ->
            state.carta?.let { carta ->
                val nuevaCantidad = maxOf(carta.cantidadEnBiblioteca - 1, 0)
                state.copy(
                    carta = carta.copy(cantidadEnBiblioteca = nuevaCantidad)
                )
            } ?: state
        }
    }
}