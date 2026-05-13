package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.repository.BibliotecaRepository
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.ui.state.CartaDetalleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartaDetalleViewModel(
    private val cartaRepository: CartaRepositoryImpl,
    private val bibliotecaRepository: BibliotecaRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getMazosUseCase: GetMazosUseCase,
    private val cartaId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartaDetalleUiState())
    val uiState: StateFlow<CartaDetalleUiState> = _uiState.asStateFlow()

    private var currentUserId: String? = null

    init {
        cargarCarta()
    }

    private fun cargarCarta() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val user = getCurrentUserUseCase()
                currentUserId = user?.id

                if (currentUserId == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "No user found"
                        )
                    }
                    return@launch
                }

                val cartasEnriquecidas = cartaRepository.getCartasConBiblioteca(currentUserId!!)
                val carta = cartasEnriquecidas.find { it.id == cartaId }

                if (carta == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Card not found"
                        )
                    }
                    return@launch
                }

                val mazos = getMazosUseCase()

                _uiState.update {
                    it.copy(
                        carta = carta,
                        mazosDisponibles = mazos,
                        isLoading = false
                    )
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

    fun toggleFavorita() {
        viewModelScope.launch {
            val carta = _uiState.value.carta ?: return@launch
            val userId = currentUserId ?: return@launch

            try {
                val nuevoEstado = !carta.esFav

                bibliotecaRepository.toggleFavorita(userId, carta.id, nuevoEstado)

                _uiState.update {
                    it.copy(carta = carta.copy(esFav = nuevoEstado))
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun incrementarCantidad() {
        viewModelScope.launch {
            val carta = _uiState.value.carta ?: return@launch
            val userId = currentUserId ?: return@launch

            try {
                bibliotecaRepository.incrementarCantidad(userId, carta.id)

                _uiState.update {
                    it.copy(carta = carta.copy(cantidadEnBiblioteca = carta.cantidadEnBiblioteca + 1))
                }

            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun decrementarCantidad() {
        viewModelScope.launch {
            val carta = _uiState.value.carta ?: return@launch
            val userId = currentUserId ?: return@launch

            if (carta.cantidadEnBiblioteca <= 0) return@launch

            try {
                bibliotecaRepository.decrementarCantidad(userId, carta.id)

                val nuevaCantidad = (carta.cantidadEnBiblioteca - 1).coerceAtLeast(0)
                _uiState.update {
                    it.copy(carta = carta.copy(cantidadEnBiblioteca = nuevaCantidad))
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun abrirDialogoSeleccionarMazo() {
        _uiState.update { it.copy(mostrarDialogoSeleccionarMazo = true) }
    }

    fun cerrarDialogoSeleccionarMazo() {
        _uiState.update { it.copy(mostrarDialogoSeleccionarMazo = false) }
    }

    fun añadirCartaAMazo(mazoId: String) {
        // TODO: Implementar cuando tengas mazos en Firestore
        cerrarDialogoSeleccionarMazo()
    }
}