package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.repository.BibliotecaRepository
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRpository
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
    private val mazoRepository: MazoRpository,
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
                        todasLasCartas = cartasEnriquecidas,
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
        viewModelScope.launch {
            val carta = _uiState.value.carta ?: return@launch
            val userId = currentUserId ?: return@launch

            try {
                val mazo = mazoRepository.getMazoById(mazoId)

                if (mazo == null) {
                    _uiState.update {
                        it.copy(errorMessage = "Deck not found")
                    }
                    cerrarDialogoSeleccionarMazo()
                    return@launch
                }

                val esCartaHuevo = carta.nivel == Nivel.LV2
                if (!esCartaHuevo && mazo.cartasNormales >= 50) {
                    _uiState.update {
                        it.copy(errorMessage = "Deck already has 50 cards")
                    }
                    cerrarDialogoSeleccionarMazo()
                    return@launch
                }


                if (esCartaHuevo && mazo.cartasHuevo >= 5) {
                    _uiState.update {
                        it.copy(errorMessage = "Deck already has maximum Digi-Eggs (5)")
                    }
                    cerrarDialogoSeleccionarMazo()
                    return@launch
                }
                val maxCopias = 4
                val copiasActuales = mazo.cartas
                    .find { it.cartaId == carta.id }?.cantidad ?: 0

                if (copiasActuales >= maxCopias) {
                    _uiState.update {
                        it.copy(
                            errorMessage = "You already have max copies (${maxCopias})"
                        )
                    }
                    cerrarDialogoSeleccionarMazo()
                    return@launch
                }

                val result = mazoRepository.añadirCartaAMazo(
                    userId = userId,
                    mazoId = mazoId,
                    cartaId = carta.id,
                    esCartaHuevo = esCartaHuevo,
                    cantidad = 1
                )

                result.fold(
                    onSuccess = {
                        cerrarDialogoSeleccionarMazo()
                        kotlinx.coroutines.delay(3000)
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(errorMessage = error.message ?: "Error")
                        }
                        cerrarDialogoSeleccionarMazo()
                    }
                )

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error: ${e.message}")
                }
                cerrarDialogoSeleccionarMazo()
            }
        }
    }
}