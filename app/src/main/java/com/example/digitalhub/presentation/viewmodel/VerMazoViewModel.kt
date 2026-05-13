package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.ui.state.VerMazoUiState
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerMazoViewModel(
    private val getMazoByIdUseCase: GetMazoByIdUseCase,
    private val getCartasUseCase: GetCartasUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val crearMazoUseCase: CrearMazoUseCase,
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
                val usuarioActual = getCurrentUserUseCase()
                val mazo = getMazoByIdUseCase(mazoId)
                val cartas = getCartasUseCase()

                if (mazo != null) {

                    _uiState.update {
                        val usuario = getUserByIdUseCase(mazo.userId)
                        it.copy(
                            mazo = mazo,
                            usuario = usuario,
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
                val mazoOriginal = _uiState.value.mazo ?: return@launch
                val usuarioActual = getCurrentUserUseCase() ?: return@launch

                val mazoCopia = mazoOriginal.copy(
                    id = "",
                    nombre = "${mazoOriginal.nombre} (Copy)",
                    userId = usuarioActual.id,
                    cartas = mazoOriginal.cartas,
                    fechaCreacion = System.currentTimeMillis(),
                    fechaModificacion = System.currentTimeMillis()
                )

                crearMazoUseCase(mazoCopia)
                    .onSuccess {
                        cerrarDialogoCopiar()
                    }
                    .onFailure { error ->
                        println("Error copying deck: ${error.message}")
                    }
            } catch (e: Exception) {
                println("Error copying deck: ${e.message}")
            }
        }
    }
}