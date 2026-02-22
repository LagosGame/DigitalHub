package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta
import com.example.digitalhub.domain.usecase.FiltrarCartasUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.presentation.ui.state.BibliotecaUiState
import com.example.digitalhub.presentation.ui.state.Selector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BibliotecaViewModel(
    private val getCartasUseCase: GetCartasUseCase,
    private val filtrarCartasUseCase: FiltrarCartasUseCase
): ViewModel()
{
    private val _uiState = MutableStateFlow(BibliotecaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCartas()
    }

    private fun fetchCartas(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val cartas = getCartasUseCase()
                _uiState.update {
                    it.copy(
                        cartas = cartas,
                        isLoading = false
                    )
                }
            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar el mensaje : ${e.message}"
                    )
                }
            }
        }
    }
    //Filtros//

    private fun aplicarFiltros(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val cartasFiltradas = filtrarCartasUseCase(
                    color = _uiState.value.colorFiltro,
                    coste = _uiState.value.costeFiltro,
                    rareza = _uiState.value.rarezaFiltro,
                    tipo = _uiState.value.tipoFiltro,
                    nivel = _uiState.value.nivelFiltro,
                    expansion = _uiState.value.expansionFiltro,
                    soloFav = _uiState.value.soloFav,
                    soloAlt = _uiState.value.soloAlt
                )
                _uiState.update {
                    it.copy(
                        cartas = cartasFiltradas,
                        isLoading = false
                    )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al filtrar : ${e.message}"
                    )
                }
            }
        }
    }
    //Abir y cerrar los selectores de filtros

    fun abrirSelector(selector: Selector){
        _uiState.update {
            it.copy(
                selectorAbierto = if (it.selectorAbierto == selector) null else selector
            )
        }
    }

    fun selectColor(color : ColorCarta){
        _uiState.update {
            it.copy(
                colorFiltro = color,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun selectCoste(coste : Int) {
        _uiState.update {
            it.copy(
                costeFiltro = coste,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun selectRareza(rareza : RarezaCarta) {
        _uiState.update {
            it.copy(
                rarezaFiltro = rareza,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun selectTipo(tipo : TipoCarta) {
        _uiState.update {
            it.copy(
                tipoFiltro = tipo,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun selectNivel(nivel : Nivel) {
        _uiState.update {
            it.copy(
                nivelFiltro = nivel,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun selectExpansion(expansion : Expansion) {
        _uiState.update {
            it.copy(
                expansionFiltro = expansion,
                selectorAbierto = null
            )
        }
        aplicarFiltros()
    }
    fun activarFav() {
        _uiState.update {
            it.copy(soloFav = !it.soloFav)
        }
        aplicarFiltros()
    }
    fun activarAlt() {
        _uiState.update {
            it.copy(soloAlt = !it.soloAlt)
        }
        aplicarFiltros()
    }
    //Busqueda nombre//
    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
        viewModelScope.launch {
            val todasLasCartas = getCartasUseCase()
            val cartasFiltradas = if (texto.isBlank()) {
                todasLasCartas
            } else {
                todasLasCartas.filter { carta ->
                    carta.nombre.contains(texto, ignoreCase = true)
                }
            }
            _uiState.update { it.copy(cartas = cartasFiltradas) }
        }
    }

    //Resetear filtros//
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
                selectorAbierto = null,
                busqueda = ""
            )
        }
        fetchCartas()
    }
}