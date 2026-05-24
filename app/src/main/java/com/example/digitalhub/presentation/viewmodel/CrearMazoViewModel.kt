package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.*
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.usecase.ActualizarMazoUseCase
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.ui.state.CrearMazoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearMazoViewModel(
    private val cartaRepository: CartaRepositoryImpl,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createMazoUseCase: CrearMazoUseCase,
    private val updateMazoUseCase: ActualizarMazoUseCase,
    private val getMazoByIdUseCase: GetMazoByIdUseCase,
    private val mazoId: String? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearMazoUiState())
    val uiState: StateFlow<CrearMazoUiState> = _uiState.asStateFlow()

    private val _eventoNavegacion = MutableStateFlow<EventoNavegacion?>(null)
    val eventoNavegacion: StateFlow<EventoNavegacion?> = _eventoNavegacion.asStateFlow()
    sealed class EventoNavegacion {
        object VolverAtras : EventoNavegacion()
    }

    fun limpiarEvento() {
        _eventoNavegacion.value = null
    }
    private var currentUserId: String? = null
    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingBiblioteca = true) }

            try {
                //Obtener usuario actual
                val user = getCurrentUserUseCase()
                currentUserId = user?.id

                if (currentUserId == null) {
                    _uiState.update {
                        it.copy(
                            isLoadingBiblioteca = false,
                            errorMessage = "Usuario no autenticado"
                        )
                    }
                    return@launch
                }

                //Cargar
                val cartasBiblioteca = cartaRepository.getCartasConBiblioteca(currentUserId!!)
                    .filter { it.cantidadEnBiblioteca > 0 }

                if (mazoId != null) {
                    val mazo = getMazoByIdUseCase(mazoId)
                    if (mazo != null) {
                        _uiState.update {
                            it.copy(
                                mazoId = mazo.id,
                                nombreMazo = mazo.nombre,
                                cartasNormales = mazo.cartas.filter { carta ->
                                    val cartaCompleta = cartasBiblioteca.find { c -> c.id == carta.cartaId }
                                    cartaCompleta?.tipo != TipoCarta.DIGIEGG
                                }.toMutableList(),
                                cartasHuevo = mazo.cartas.filter { carta ->
                                    val cartaCompleta = cartasBiblioteca.find { c -> c.id == carta.cartaId }
                                    cartaCompleta?.tipo == TipoCarta.DIGIEGG
                                }.toMutableList(),
                                totalNormales = mazo.cartasNormales,
                                totalHuevo = mazo.cartasHuevo,
                                portadaId = mazo.portadaId,
                                esFavorito = mazo.esFavorito,
                                cartasBiblioteca = cartasBiblioteca,
                                isLoadingBiblioteca = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                cartasBiblioteca = cartasBiblioteca,
                                isLoadingBiblioteca = false
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            cartasBiblioteca = cartasBiblioteca,
                            isLoadingBiblioteca = false
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingBiblioteca = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombreMazo = nombre, errorMessage = null) }
    }

    private fun getBaseCardId(cartaId: String): String {
        return if (cartaId.endsWith("A", ignoreCase = true)) {
            cartaId.dropLast(1)
        } else {
            cartaId
        }
    }
    private fun contarCopiasDeCartaBase(cartaId: String, esHuevo: Boolean): Int {
        val baseId = getBaseCardId(cartaId)
        val lista = if (esHuevo) _uiState.value.cartasHuevo else _uiState.value.cartasNormales

        return lista
            .filter { getBaseCardId(it.cartaId) == baseId }
            .sumOf { it.cantidad }
    }
    fun añadirCarta(carta: Carta) {
        val state = _uiState.value
        val esHuevo = carta.tipo == TipoCarta.DIGIEGG

        // Validaciones de límites generales
        if (esHuevo && state.totalHuevo >= 5) {
            _uiState.update { it.copy(errorMessage = "Max 5 Digi-Eggs") }
            return
        }

        if (!esHuevo && state.totalNormales >= 50) {
            _uiState.update { it.copy(errorMessage = "Max 50 normal cards") }
            return
        }

        val copiasExistentes = contarCopiasDeCartaBase(carta.id, esHuevo)

        if (copiasExistentes >= 4) {
            val baseId = getBaseCardId(carta.id)
            _uiState.update {
                it.copy(errorMessage = "Max 4 copies of $baseId (including alternate arts)")
            }
            return
        }

        //Añadir carta
        if (esHuevo) {
            val nuevasCartasHuevo = state.cartasHuevo.toMutableList()
            val index = nuevasCartasHuevo.indexOfFirst { it.cartaId == carta.id }

            if (index != -1) {
                nuevasCartasHuevo[index] = nuevasCartasHuevo[index].copy(cantidad = nuevasCartasHuevo[index].cantidad + 1)
            } else {
                nuevasCartasHuevo.add(CartaEnMazo(carta.id, 1))
            }

            _uiState.update {
                it.copy(
                    cartasHuevo = nuevasCartasHuevo,
                    totalHuevo = nuevasCartasHuevo.sumOf { c -> c.cantidad },
                    errorMessage = null
                )
            }
        } else {
            val nuevasCartasNormales = state.cartasNormales.toMutableList()
            val index = nuevasCartasNormales.indexOfFirst { it.cartaId == carta.id }

            if (index != -1) {
                nuevasCartasNormales[index] = nuevasCartasNormales[index].copy(cantidad = nuevasCartasNormales[index].cantidad + 1)
            } else {
                nuevasCartasNormales.add(CartaEnMazo(carta.id, 1))
            }

            _uiState.update {
                it.copy(
                    cartasNormales = nuevasCartasNormales,
                    totalNormales = nuevasCartasNormales.sumOf { c -> c.cantidad },
                    errorMessage = null
                )
            }
        }
    }

    fun quitarCarta(cartaId: String, esHuevo: Boolean) {
        val state = _uiState.value

        if (esHuevo) {
            val nuevasCartasHuevo = state.cartasHuevo.toMutableList()
            val index = nuevasCartasHuevo.indexOfFirst { it.cartaId == cartaId }

            if (index != -1) {
                val carta = nuevasCartasHuevo[index]
                if (carta.cantidad > 1) {
                    nuevasCartasHuevo[index] = carta.copy(cantidad = carta.cantidad - 1)
                } else {
                    nuevasCartasHuevo.removeAt(index)
                }

                _uiState.update {
                    it.copy(
                        cartasHuevo = nuevasCartasHuevo,
                        totalHuevo = nuevasCartasHuevo.sumOf { c -> c.cantidad }
                    )
                }
            }
        } else {
            val nuevasCartasNormales = state.cartasNormales.toMutableList()
            val index = nuevasCartasNormales.indexOfFirst { it.cartaId == cartaId }

            if (index != -1) {
                val carta = nuevasCartasNormales[index]
                if (carta.cantidad > 1) {
                    nuevasCartasNormales[index] = carta.copy(cantidad = carta.cantidad - 1)
                } else {
                    nuevasCartasNormales.removeAt(index)
                }

                _uiState.update {
                    it.copy(
                        cartasNormales = nuevasCartasNormales,
                        totalNormales = nuevasCartasNormales.sumOf { c -> c.cantidad }
                    )
                }
            }
        }
    }

    fun establecerPortada(cartaId: String) {
        _uiState.update { it.copy(portadaId = cartaId) }
    }

    fun toggleFavorito() {
        _uiState.update { it.copy(esFavorito = !it.esFavorito) }
    }

    fun limpiar() {
        _uiState.update {
            CrearMazoUiState(
                cartasBiblioteca = it.cartasBiblioteca
            )
        }
    }

    fun guardarMazo(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val userId = currentUserId

            // Validaciones
            if (state.nombreMazo.isBlank()) {
                _uiState.update { it.copy(errorMessage = "Deck name required") }
                return@launch
            }

            if (userId == null) {
                _uiState.update { it.copy(errorMessage = "User not authenticated") }
                return@launch
            }

            try {
                val todasLasCartas = state.cartasNormales + state.cartasHuevo
                val colores = calcularColoresMazo(todasLasCartas)

                if (mazoId == null) {
                    val mazo = Mazo(
                        id = "",
                        nombre = state.nombreMazo,
                        userId = userId,
                        colores = colores,
                        cartasNormales = state.totalNormales,
                        cartasHuevo = state.totalHuevo,
                        portadaId = state.portadaId,
                        cartas = todasLasCartas,
                        esFavorito = state.esFavorito
                    )

                    createMazoUseCase(mazo)
                        .onSuccess {
                            _eventoNavegacion.value = EventoNavegacion.VolverAtras
                            onSuccess()
                        }
                        .onFailure { error ->
                            println("Error: ${error.message}")
                            _uiState.update { it.copy(errorMessage = error.message) }
                        }
                } else {
                    val mazoExistente = getMazoByIdUseCase(mazoId)

                    val mazo = Mazo(
                        id = mazoId,
                        nombre = state.nombreMazo,
                        userId = userId,
                        colores = colores,
                        cartasNormales = state.totalNormales,
                        cartasHuevo = state.totalHuevo,
                        portadaId = state.portadaId,
                        cartas = todasLasCartas,
                        esFavorito = state.esFavorito,
                        descripcion = mazoExistente?.descripcion ?: "",
                        estrategias = mazoExistente?.estrategias ?: emptyList(),
                        cartasImportantes = mazoExistente?.cartasImportantes ?: emptyList(),
                        tags = mazoExistente?.tags ?: emptyList(),
                        estadisticas = mazoExistente?.estadisticas ?: Estadisticas()
                    )

                    updateMazoUseCase(mazo)
                        .onSuccess {
                            _eventoNavegacion.value = EventoNavegacion.VolverAtras
                            onSuccess()
                        }
                        .onFailure { error ->
                            println("Error: ${error.message}")
                            _uiState.update { it.copy(errorMessage = error.message) }
                        }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun calcularColoresMazo(cartas: List<CartaEnMazo>): List<ColorCarta> {
        val cartasDelMazo = cartas.mapNotNull { cartaEnMazo ->
            _uiState.value.cartasBiblioteca.find { it.id == cartaEnMazo.cartaId }
        }
        val coloresContador = mutableMapOf<ColorCarta, Int>()
        cartasDelMazo.forEach { carta ->
            carta.color.forEach { color ->
                coloresContador[color] = (coloresContador[color] ?: 0) + 1
            }
        }
        return coloresContador.entries
            .sortedByDescending { it.value }
            .map { it.key }
    }
}