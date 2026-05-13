package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.EliminarMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.ui.state.ConstruirMazoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConstruirMazoViewModel(
    private val getMazosUseCase: GetMazosUseCase,
    private val eliminarMazoUseCase: EliminarMazoUseCase,
    private val getCartasUseCase: GetCartasUseCase
): ViewModel()
{
    private val _uiState = MutableStateFlow(ConstruirMazoUiState())
    val uiState = _uiState.asStateFlow()

//PARA EL DIALOGO
    private val _mazoAEliminar = MutableStateFlow<String?>(null)
    val mazoAEliminar = _mazoAEliminar.asStateFlow()


    init {
        fetchMazos()
        fetchCartas()
    }
    fun fetchMazos(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val mazos = getMazosUseCase()
                _uiState.update {
                    it.copy(
                        mazos = mazos,
                        isLoading = false
                    )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error fetching decks: ${e.message}"
                    )
                }
            }
        }
    }
    private fun fetchCartas() {
        viewModelScope.launch {
            try {
                val cartas = getCartasUseCase()
                _uiState.update { it.copy(todasLasCartas = cartas) }
            } catch (e: Exception) {
                println("Error loading cards: ${e.message}")
            }
        }
    }

    fun mostrarDialogoEliminar(mazoId: String) {
        _mazoAEliminar.value = mazoId
    }

    fun ocultarDialogoEliminar() {
        _mazoAEliminar.value = null
    }
    fun confirmarEliminacion() {
        val mazoId = _mazoAEliminar.value ?: return
        viewModelScope.launch {
            try {
                eliminarMazoUseCase(mazoId)
                ocultarDialogoEliminar()
                fetchMazos()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error trying to delete the deck: ${e.message}")
                }
            }
        }
    }
    fun recargarMazos() {
        fetchMazos()
    }
}