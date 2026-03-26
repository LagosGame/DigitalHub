package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.ui.state.VerMazoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerMazoViewModel(
    private val getMazoByIdUseCase: GetMazoByIdUseCase,
    private val getCartasUseCase: GetCartasUseCase,
    private val mazoId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerMazoUiState())
    val uiState = _uiState.asStateFlow()

    private val _mostrarDialogoCopiar = MutableStateFlow(false)
    val mostrarDialogoCopiar = _mostrarDialogoCopiar.asStateFlow()


    init {
        cargarMazo()
    }

    private fun cargarMazo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val mazo = getMazoByIdUseCase(mazoId)
                val cartas = getCartasUseCase()

                if (mazo != null) {
                    _uiState.update {
                        it.copy(
                            mazo = mazo,
                            todasLasCartas = cartas,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Deck not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    fun abrirDialogoCopiar() {
        _mostrarDialogoCopiar.value = true
    }

    fun cerrarDialogoCopiar() {
        _mostrarDialogoCopiar.value = false
    }


    fun confirmarCopia() {
        viewModelScope.launch {
            try {
                println(" Deck copy: ${_uiState.value.mazo?.id}")
                cerrarDialogoCopiar()
            } catch (e: Exception) {
                println("Error copying deck: ${e.message}")
            }
        }
    }
}