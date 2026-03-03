package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.R
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.CartaEnMazo
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.TipoCarta
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.ui.state.CrearMazoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearMazoViewModel(
    private val getCartasUseCase: GetCartasUseCase,
    private val crearMazoUseCase: CrearMazoUseCase,
    private val getMazoByIdUseCase: GetMazoByIdUseCase,
    private val mazoIdInicial: String? = null
): ViewModel()
{
    private val _uiState = MutableStateFlow(CrearMazoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchBiblioteca()
        if (mazoIdInicial != null) {
            cargarMazoExistente(mazoIdInicial)
        }
    }

    private fun cargarMazoExistente(mazoId: String) {
        viewModelScope.launch {
            try {
                val mazo = getMazoByIdUseCase(mazoId)

                if (mazo != null) {
                    println(mazo.nombre)
                    println(mazo.cartas.size)
                    println(mazo.cartasNormales)
                    println(mazo.cartasHuevo)

                    kotlinx.coroutines.delay(500)


                    val cartasNormales = mazo.cartas.filter { cartaEnMazo ->
                        val carta = _uiState.value.cartasBiblioteca.find { it.id == cartaEnMazo.cartaId }
                        carta?.tipo != TipoCarta.DIGIEGG
                    }

                    val cartasHuevo = mazo.cartas.filter { cartaEnMazo ->
                        val carta = _uiState.value.cartasBiblioteca.find { it.id == cartaEnMazo.cartaId }
                        carta?.tipo == TipoCarta.DIGIEGG
                    }


                    val totalNormales = cartasNormales.sumOf { it.cantidad }
                    val totalHuevo = cartasHuevo.sumOf { it.cantidad }

                    println("${cartasNormales.size} (Total: $totalNormales)")
                    println("${cartasHuevo.size} (Total: $totalHuevo)")


                    val cartaPortada = _uiState.value.cartasBiblioteca.find { carta ->
                        carta.imagenId == mazo.portadaId
                    }

                    _uiState.update {
                        it.copy(
                            mazoId = mazo.id,
                            nombreMazo = mazo.nombre,
                            cartasNormales = cartasNormales,
                            cartasHuevo = cartasHuevo,
                            totalNormales = totalNormales,
                            totalHuevo = totalHuevo,
                            cartaIdPortada = cartaPortada?.id
                        )
                    }
                } else {
                    println("Deck not found: $mazoId")
                }
            } catch (e: Exception) {
                println("Error : ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun fetchBiblioteca(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingBiblioteca = false) }
            try {
                val cartas = getCartasUseCase()
                    .filter { it.cantidadEnBiblioteca>0 }
                _uiState.update {
                    it.copy(
                        cartasBiblioteca = cartas,
                        isLoadingBiblioteca = false
                    )
                }
            }catch (e : Exception){
                _uiState.update {
                    it.copy(
                        isLoadingBiblioteca = false,
                        errorMessage = "Error loading library : ${e.message}"
                    )
                }
            }
        }
    }
    fun cambiarNombreMazo(nuevoNombre : String){
        _uiState.update {
            it.copy(nombreMazo = nuevoNombre.take(20))
        }
    }

    fun añadirCarta(carta: Carta){
        val esHuevo = carta.tipo == TipoCarta.DIGIEGG

        //limites//

        if (!esHuevo && _uiState.value.totalNormales >= 50){
            _uiState.update { it.copy(errorMessage = "50 normal cards max.") }
            return
        }
        if (esHuevo && _uiState.value.totalHuevo >= 5){
            _uiState.update { it.copy(errorMessage = "5 digi-egg cards max.") }
            return
        }

        //mirar en tu bibliotecs//

        val copiasDisponibles = carta.cantidadEnBiblioteca
        val copiasEnMazo = copiasEnMazo(carta.id)

        if (copiasEnMazo >= copiasDisponibles){
            _uiState.update { it.copy(errorMessage = "You dont have more copies of this card.") }
            return
        }
        if (copiasEnMazo>=4){
            _uiState.update { it.copy(errorMessage = "Max 4 copies of each card") }
            return
        }

        val lista = if (esHuevo) _uiState.value.cartasHuevo else _uiState.value.cartasNormales
        val index = lista.indexOfFirst { it.cartaId==carta.id }

        val listaNueva = if (index != -1){
            lista.toMutableList().also {
                val item = it[index]
                it[index] = item.copy(cantidad = item.cantidad +1)
            }
        } else {
            lista + CartaEnMazo(cartaId = carta.id, cantidad = 1)
        }

        _uiState.update {
            if (esHuevo){
                it.copy(
                    cartasHuevo = listaNueva,
                    totalHuevo = listaNueva.sumOf { cant -> cant.cantidad },
                    errorMessage = null
                )
            }else{
                it.copy(
                    cartasNormales = listaNueva,
                    totalNormales = listaNueva.sumOf { cant -> cant.cantidad },
                    errorMessage = null
                )
            }
        }

    }

    private val _mostrarDialogoPortada = MutableStateFlow(false)
    val mostrarDialogoPortada = _mostrarDialogoPortada.asStateFlow()

    fun abrirDialogoPortada() {
        _mostrarDialogoPortada.value = true
    }

    fun cerrarDialogoPortada() {
        _mostrarDialogoPortada.value = false
    }
    fun establecerPortada(cartaId: String) {
        _uiState.update {
            it.copy(cartaIdPortada = cartaId)
        }
    }

    fun quitarCarta(cartaId : String, esHuevo: Boolean){
        val lista = if (esHuevo) _uiState.value.cartasHuevo else _uiState.value.cartasNormales
        val index = lista.indexOfFirst { it.cartaId == cartaId }
        if (index == -1) return

        val item = lista[index]

        val listaNueva = if (item.cantidad > 1){
            lista.toMutableList().also {
                it[index] = item.copy(cantidad = item.cantidad -1)
            }
        }else{
            lista.toMutableList().also { it.removeAt(index) }
        }
        _uiState.update {
            if (esHuevo){
                it.copy(
                    cartasHuevo = listaNueva,
                    totalHuevo = listaNueva.sumOf { cant -> cant.cantidad },
                )
            }else{
                it.copy(
                    cartasNormales = listaNueva,
                    totalNormales = listaNueva.sumOf { cant -> cant.cantidad },
                )
            }
        }
    }

    fun copiasEnMazo(cartaId : String): Int{
        val normales = _uiState.value.cartasNormales.firstOrNull { it.cartaId == cartaId}?.cantidad ?: 0
        val huevos = _uiState.value.cartasHuevo.firstOrNull { it.cartaId == cartaId}?.cantidad ?: 0
        return normales + huevos
    }

    fun guardarMazo(onSuccess:()-> Unit){
        viewModelScope.launch {
            //validacion//
            if (_uiState.value.nombreMazo.isBlank()){
                _uiState.update { it.copy(errorMessage = "Deck needs a name") }
                return@launch
            }
            if (_uiState.value.totalNormales < 50 || _uiState.value.totalHuevo < 4){
                _uiState.update { it.copy(errorMessage = "Deck needs at least 50 normal cards or 4 digi-egg cards") }
                return@launch
            }

            try {
                val todasLasCartas = _uiState.value.cartasNormales+_uiState.value.cartasHuevo
                val colores = todasLasCartas
                    .mapNotNull { cartaEnMazo ->
                        _uiState.value.cartasBiblioteca.find { it.id == cartaEnMazo.cartaId }
                    }
                    .flatMap { it.color }
                    .distinct()
                //Carta de la portadfa//
                val cartaPortadaId = _uiState.value.cartaIdPortada
                    ?: _uiState.value.cartasNormales.firstOrNull()?.cartaId

                val portada = _uiState.value.cartasBiblioteca.find { it.id == cartaPortadaId }

                val mazo = Mazo(
                    id = _uiState.value.mazoId,
                    nombre = _uiState.value.nombreMazo,
                    colores = colores,
                    cartasNormales = _uiState.value.totalNormales,
                    cartasHuevo = _uiState.value.totalHuevo,
                    portadaId = portada?.imagenId ?: R.drawable.bt1025,
                    tags = emptyList(),
                    cartas = _uiState.value.cartasNormales + _uiState.value.cartasHuevo,
                    descripcion = "",
                    estrategias = emptyList(),
                    cartasImportantes = emptyList(),
                    estadisticas = com.example.digitalhub.domain.model.Estadisticas()
                )
                crearMazoUseCase(mazo)
                onSuccess()
            }
            catch (e : Exception){
                _uiState.update {
                    it.copy(errorMessage = "Error saving the deck: ${e.message}")
                }
            }
        }
    }
    fun limpiarMazo() {
        _uiState.update {
            it.copy(
                cartasNormales = emptyList(),
                cartasHuevo = emptyList(),
                totalNormales = 0,
                totalHuevo = 0
            )
        }
    }
}