package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetAllMazosUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.presentation.ui.state.ListaMazosUiState
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaMazosViewModel(
    private val getAllMazosUseCase: GetAllMazosUseCase,
    private val getCartasUseCase: GetCartasUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createMazoUseCase: CrearMazoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaMazosUiState())
    val uiState = _uiState.asStateFlow()

    private val _mazoACopiar = MutableStateFlow<String?>(null)
    val mazoACopiar = _mazoACopiar.asStateFlow()

    private var currentUserId: String? = null

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
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
                val todosMazos = getAllMazosUseCase()
                val mazosPropios = todosMazos.filter { it.userId == currentUserId }
                val mazosOtros = todosMazos.filter { it.userId != currentUserId }
                val cartas = getCartasUseCase()
                val usuariosIds = todosMazos.map { it.userId }.distinct()
                val usuarios = usuariosIds.mapNotNull { userId ->
                    getUserByIdUseCase(userId)
                }.associateBy { it.id }

                _uiState.update {
                    it.copy(
                        mazos = mazosOtros,
                        mazosPropios = mazosPropios,
                        mazosOtros = mazosOtros,
                        todasLasCartas = cartas,
                        usuarios = usuarios,
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
        val userId = currentUserId ?: return

        viewModelScope.launch {
            try {
                val mazoOriginal = _uiState.value.mazos.find { it.id == mazoId }
                    ?: _uiState.value.mazosOtros.find { it.id == mazoId }

                if (mazoOriginal == null) {
                    println("Deck not found")
                    return@launch
                }
                val mazoCopia = mazoOriginal.copy(
                    id = "",
                    nombre = "${mazoOriginal.nombre} (Copy)",
                    userId = userId,
                    cartas = mazoOriginal.cartas,
                    fechaCreacion = System.currentTimeMillis(),
                    fechaModificacion = System.currentTimeMillis()
                )
                createMazoUseCase(mazoCopia)
                    .onSuccess {
                        ocultarDialogoCopiar()
                        cargarDatos()
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(errorMessage = "Error copying deck: ${error.message}")
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error copying deck: ${e.message}")
                }
            }
        }
    }

    fun recargarMazos() {
        cargarDatos()
    }

    fun mostrarMisMazos() {
        _uiState.update {
            it.copy(mazos = it.mazosPropios)
        }
    }

    fun mostrarTodosMazos() {
        _uiState.update {
            it.copy(mazos = it.mazosPropios + it.mazosOtros)
        }
    }
}