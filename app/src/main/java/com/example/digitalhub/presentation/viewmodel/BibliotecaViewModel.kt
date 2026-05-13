package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.*
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.presentation.ui.state.BibliotecaUiState
import com.example.digitalhub.presentation.ui.state.Selector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BibliotecaViewModel(
    private val cartaRepository: CartaRepositoryImpl,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BibliotecaUiState())
    val uiState: StateFlow<BibliotecaUiState> = _uiState.asStateFlow()

    private var currentUserId: String? = null
    private var todasLasCartas: List<Carta> = emptyList()

    init {
        cargarBiblioteca()
    }

    private fun cargarBiblioteca() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {

                val user = getCurrentUserUseCase()
                currentUserId = user?.id

                if (currentUserId == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Usuario no autenticado"
                        )
                    }
                    return@launch
                }

                todasLasCartas = cartaRepository.getCartasConBiblioteca(currentUserId!!)

                _uiState.update {
                    it.copy(
                        cartas = todasLasCartas,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    private fun aplicarFiltros() {
        viewModelScope.launch {
            try {
                val state = _uiState.value
                var cartasFiltradas = todasLasCartas

                state.colorFiltro?.let { color ->
                    cartasFiltradas = cartasFiltradas.filter { it.color.contains(color) }
                }

                state.costeFiltro?.let { coste ->
                    cartasFiltradas = cartasFiltradas.filter { it.coste == coste }
                }

                state.rarezaFiltro?.let { rareza ->
                    cartasFiltradas = cartasFiltradas.filter { it.rareza == rareza }
                }

                state.tipoFiltro?.let { tipo ->
                    cartasFiltradas = cartasFiltradas.filter { it.tipo == tipo }
                }

                state.nivelFiltro?.let { nivel ->
                    cartasFiltradas = cartasFiltradas.filter { it.nivel == nivel }
                }

                state.expansionFiltro?.let { expansion ->
                    cartasFiltradas = cartasFiltradas.filter { it.expansion == expansion }
                }

                if (state.soloFav) {
                    cartasFiltradas = cartasFiltradas.filter { it.esFav }
                }

                if (state.soloAlt) {
                    cartasFiltradas = cartasFiltradas.filter { it.esAlt }
                }

                if (state.soloMiBiblioteca) {
                    cartasFiltradas = cartasFiltradas.filter { it.cantidadEnBiblioteca > 0 }
                }

                if (state.busqueda.isNotBlank()) {
                    cartasFiltradas = cartasFiltradas.filter {
                        it.nombre.contains(state.busqueda, ignoreCase = true)
                    }
                }

                _uiState.update { it.copy(cartas = cartasFiltradas) }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun abrirSelector(selector: Selector) {
        _uiState.update {
            it.copy(
                selectorAbierto = if (it.selectorAbierto == selector) null else selector
            )
        }
    }

    fun selectColor(color: ColorCarta?) {
        _uiState.update {
            it.copy(
                colorFiltro = color,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun selectCoste(coste: Int?) {
        _uiState.update {
            it.copy(
                costeFiltro = coste,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun selectRareza(rareza: RarezaCarta?) {
        _uiState.update {
            it.copy(
                rarezaFiltro = rareza,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun selectTipo(tipo: TipoCarta?) {
        _uiState.update {
            it.copy(
                tipoFiltro = tipo,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun selectNivel(nivel: Nivel?) {
        _uiState.update {
            it.copy(
                nivelFiltro = nivel,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun selectExpansion(expansion: Expansion?) {
        _uiState.update {
            it.copy(
                expansionFiltro = expansion,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }

    fun activarFav() {
        _uiState.update { it.copy(soloFav = !it.soloFav) }
        aplicarFiltros()
    }

    fun activarAlt() {
        _uiState.update { it.copy(soloAlt = !it.soloAlt) }
        aplicarFiltros()
    }

    fun activarSoloMiBiblioteca() {
        _uiState.update { it.copy(soloMiBiblioteca = !it.soloMiBiblioteca) }
        aplicarFiltros()
    }

    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
        aplicarFiltros()
    }

    fun recargarBiblioteca() {
        cargarBiblioteca()
    }

    fun limpiarFiltros() {
        _uiState.update {
            it.copy(
                colorFiltro = null,
                costeFiltro = null,
                rarezaFiltro = null,
                tipoFiltro = null,
                nivelFiltro = null,
                expansionFiltro = null,
                soloFav = false,
                soloAlt = false,
                soloMiBiblioteca = false,
                busqueda = "",
                cartas = todasLasCartas
            )
        }
    }
}