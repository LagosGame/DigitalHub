package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.Estrategia
import com.example.digitalhub.domain.usecase.ActualizarMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.ui.state.DetalleMazoUiState
import com.example.digitalhub.presentation.ui.state.EstadisticasEdit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleMazoViewModel(
    private val getMazoByIdUseCase : GetMazoByIdUseCase,
    private val getCartasUseCase: GetCartasUseCase,
    private val actualizarMazoUseCase: ActualizarMazoUseCase,
    private val mazoId : String
): ViewModel() {

    private val _uiStatw = MutableStateFlow(DetalleMazoUiState())
    val uiState = _uiStatw.asStateFlow()

    private val _eventoNavegacion = MutableStateFlow<EventoNavegacion?>(null)
    val eventoNavegacion:  StateFlow<EventoNavegacion?> = _eventoNavegacion.asStateFlow()

    sealed class EventoNavegacion {
        object VolverAtras : EventoNavegacion()
    }

    fun limpiarEvento() {
        _eventoNavegacion.value = null
    }

    init {
        fetchMazo()
    }

    private fun fetchMazo(){
        viewModelScope.launch {
            _uiStatw.update { it.copy(isLoading = true) }
            try {
                val mazo = getMazoByIdUseCase(mazoId)
                val cartas = getCartasUseCase()

                if (mazo != null){
                    _uiStatw.update {
                        it.copy(
                            mazo = mazo,
                            descripcion = mazo.descripcion,
                            estrategias = mazo.estrategias,
                            cartasImportantes = mazo.cartasImportantes,
                            tags = mazo.tags,
                            estadisticas = EstadisticasEdit(
                                ataque = mazo.estadisticas.ataque,
                                defensa = mazo.estadisticas.defensa,
                                consistencia = mazo.estadisticas.consistencia,
                                versatilidad = mazo.estadisticas.versatilidad,
                                recuperacion = mazo.estadisticas.recuperacion,
                            ),
                            todasLasCartas = cartas,
                            isLoading = false
                        )
                    }
                }
                else{
                    _uiStatw.update { it.copy(isLoading = false, errorMessage = "Deck not found") }
                }
            }
            catch (e : Exception){
                _uiStatw.update { it.copy(isLoading = false, errorMessage = "Error loading : ${e.message}") }
            }
        }
    }

    fun actualizarDescripcion(nuevaDescripcion : String){
        _uiStatw.update { it.copy(descripcion = nuevaDescripcion) }
        guardarCambios()
    }
    fun toggleDialogoDescripcion() {
        _uiStatw.update { it.copy(mostrarDialogoDescripcion = !it.mostrarDialogoDescripcion) }
    }

    fun añadirEstrategia(titulo:String, cartasIds: List<String>){
        val nuevaEstrategia = Estrategia(titulo,cartasIds)
        _uiStatw.update {
            it.copy(estrategias = it.estrategias + nuevaEstrategia)
        }
        guardarCambios()
    }
    fun eliminarEstrategia(index: Int) {
        _uiStatw.update {
            it.copy(estrategias = it.estrategias.filterIndexed { i, _ -> i != index })
        }
        guardarCambios()
    }
    fun toggleDialogoEstrategia() {
        _uiStatw.update { it.copy(mostrarDialogoEstrategia = !it.mostrarDialogoEstrategia) }
    }


    fun añadirCartaImportante(cartaId: String) {
        if (!_uiStatw.value.cartasImportantes.contains(cartaId)) {
            _uiStatw.update {
                it.copy(cartasImportantes = it.cartasImportantes + cartaId)
            }
        }
        guardarCambios()
    }
    fun eliminarCartaImportante(cartaId: String) {
        _uiStatw.update {
            it.copy(cartasImportantes = it.cartasImportantes.filter { id -> id != cartaId })
        }
        guardarCambios()
    }
    fun toggleDialogoCartasImportantes() {
        _uiStatw.update { it.copy(mostrarDialogoCartasImportantes = !it.mostrarDialogoCartasImportantes) }
    }

    fun añadirTag(tag: String) {
        if (!_uiStatw.value.tags.contains(tag) && tag.isNotBlank()) {
            _uiStatw.update {
                it.copy(tags = it.tags + tag)
            }
        }
        guardarCambios()
    }

    fun eliminarTag(tag: String) {
        _uiStatw.update {
            it.copy(tags = it.tags.filter { t -> t != tag })
        }
        guardarCambios()
    }

    fun toggleDialogoTags() {
        _uiStatw.update { it.copy(mostrarDialogoTags = !it.mostrarDialogoTags) }
    }


    fun actualizarEstadistica(campo: String, valor: Int) {
        val nuevoValor = valor.coerceIn(1, 10)
        _uiStatw.update {
            val stats = it.estadisticas
            val nuevasStats = when (campo) {
                "ATTACK" -> stats.copy(ataque = nuevoValor)
                "DEFENSE" -> stats.copy(defensa = nuevoValor)
                "CONSISTENCY" -> stats.copy(consistencia = nuevoValor)
                "VERSATILITY" -> stats.copy(versatilidad = nuevoValor)
                "COMEBACK" -> stats.copy(recuperacion = nuevoValor)
                else -> stats
            }
            it.copy(estadisticas = nuevasStats)
        }
        guardarCambios()
    }

    fun toggleDialogoEstadisticas() {
        _uiStatw.update { it.copy(mostrarDialogoEstadisticas = !it.mostrarDialogoEstadisticas) }
    }


    //guardar IMPORTANTE

    fun guardarCambios() {
        viewModelScope.launch {
            val mazo = _uiStatw.value.mazo ?: return@launch

            try {
                val mazoActualizado = mazo.copy(
                    descripcion = _uiStatw.value.descripcion,
                    estrategias = _uiStatw.value.estrategias,
                    cartasImportantes = _uiStatw.value.cartasImportantes,
                    tags = _uiStatw.value.tags,
                    estadisticas = com.example.digitalhub.domain.model.Estadisticas(
                        ataque = _uiStatw.value.estadisticas.ataque,
                        defensa = _uiStatw.value.estadisticas.defensa,
                        consistencia = _uiStatw.value.estadisticas.consistencia,
                        versatilidad = _uiStatw.value.estadisticas.versatilidad,
                        recuperacion = _uiStatw.value.estadisticas.recuperacion
                    )
                )

                actualizarMazoUseCase(mazoActualizado)
                    .onSuccess {
                        _uiStatw.update { it.copy(mazo = mazoActualizado) }
                    }
                    .onFailure { error ->
                        error.printStackTrace()
                        _uiStatw.update {
                            it.copy(errorMessage = "Error: ${error.message}")
                        }
                    }
            } catch (e: Exception) {
                _uiStatw.update {
                    it.copy(errorMessage = "Error: ${e.message}")
                }
            }
        }
    }
}