package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.presentation.ui.state.ListaMazosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaMazosViewModel(
    private val getMazosUseCase: GetMazosUseCase,
    private val getCartasUseCase: GetCartasUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ListaMazosUiState())
    val uiState = _uiState.asStateFlow()

    private val _mazoACopiar = MutableStateFlow<String?>(null)
    val mazoACopiar = _mazoACopiar.asStateFlow()

    init {
        cargarMazos()
        cargarCartas()
    }

    private fun cargarMazos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val todosMazos = getMazosUseCase()
                _uiState.update {
                    it.copy(
                        mazos = todosMazos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error loading decks: ${e.message}"
                    )
                }
            }
        }
    }
    private fun cargarCartas() {
        viewModelScope.launch {
            try {
                val cartas = getCartasUseCase()
                _uiState.update { it.copy(todasLasCartas = cartas) }
            } catch (e: Exception) {
                println("Error loading cards: ${e.message}")
            }
        }
    }

    fun actualizarBusqueda(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun mostrarDialogoCopiar(mazoId: String) {
        _mazoACopiar.value = mazoId
    }

    fun ocultarDialogoCopiar() {
        _mazoACopiar.value = null
    }

    fun confirmarCopia() {
        val mazoId = _mazoACopiar.value ?: return
        viewModelScope.launch {
            try {
                println(" Deck copy: $mazoId")
                ocultarDialogoCopiar()
            } catch (e: Exception) {
                println("Error copying deck: ${e.message}")
            }
        }
    }
}