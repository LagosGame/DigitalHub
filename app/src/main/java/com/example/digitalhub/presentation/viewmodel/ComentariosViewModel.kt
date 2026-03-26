package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.usecase.AgregarComentarioUseCase
import com.example.digitalhub.domain.usecase.GetComentariosUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.domain.usecase.ToggleLikeComentarioUseCase
import com.example.digitalhub.presentation.ui.state.ComentariosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComentariosViewModel(
    private val getMazoByIdUseCase: GetMazoByIdUseCase,
    private val getComentariosUseCase: GetComentariosUseCase,
    private val agregarComentarioUseCase: AgregarComentarioUseCase,
    private val toggleLikeComentarioUseCase: ToggleLikeComentarioUseCase,
    private val mazoId: String,
    private val usuarioActual: User
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComentariosUiState())
    val uiState = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val mazo = getMazoByIdUseCase(mazoId)
                val comentarios = getComentariosUseCase(mazoId)

                _uiState.update {
                    it.copy(
                        mazo = mazo,
                        comentarios = comentarios,
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

    fun actualizarTextoComentario(texto: String) {
        _uiState.update { it.copy(nuevoComentario = texto) }
    }

    fun enviarComentario() {
        val texto = _uiState.value.nuevoComentario.trim()
        if (texto.isBlank()) return

        viewModelScope.launch {
            try {
                val nuevoComentario = Comentario(
                    id = "com_${System.currentTimeMillis()}",
                    mazoId = mazoId,
                    autorId = usuarioActual.id,
                    autor = usuarioActual,
                    contenido = texto,
                    likes = 0,
                    timestamp = System.currentTimeMillis(),
                    comentarioPadreId = _uiState.value.comentarioRespondiendoId
                )

                agregarComentarioUseCase(nuevoComentario)

                // Limpiar y recargar
                _uiState.update {
                    it.copy(
                        nuevoComentario = "",
                        comentarioRespondiendoId = null
                    )
                }
                cargarDatos()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error fetching data: ${e.message}")
                }
            }
        }
    }

    fun toggleLike(comentarioId: String) {
        viewModelScope.launch {
            try {
                toggleLikeComentarioUseCase(comentarioId,usuarioActual.id)
                cargarDatos()
            } catch (e: Exception) {
                println("Error toggle like: ${e.message}")
            }
        }
    }


    fun cancelarRespuesta() {
        _uiState.update { it.copy(comentarioRespondiendoId = null) }
    }

    fun abrirDialogoComentar() {
        _uiState.update {
            it.copy(
                mostrarDialogoComentar = true,
                comentarioRespondiendoId = null,
                nuevoComentario = ""
            )
        }
    }

    fun responderComentario(comentarioId: String) {
        _uiState.update {
            it.copy(
                mostrarDialogoResponder = true,
                comentarioRespondiendoId = comentarioId,
                nuevoComentario = ""
            )
        }
    }

    fun cerrarDialogos() {
        _uiState.update {
            it.copy(
                mostrarDialogoComentar = false,
                mostrarDialogoResponder = false,
                comentarioRespondiendoId = null,
                nuevoComentario = ""
            )
        }
    }
}