package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.usecase.AgregarComentarioUseCase
import com.example.digitalhub.domain.usecase.GetComentariosUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
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
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val mazoId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComentariosUiState())
    val uiState = _uiState.asStateFlow()
    private var currentUserId: String = ""
    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {


                val usuarioActual = getCurrentUserUseCase()
                currentUserId = usuarioActual?.id ?: ""

                val mazo = getMazoByIdUseCase(mazoId)

                var comentarios = getComentariosUseCase(mazoId)

                val autoresIds = (comentarios.map { it.autorId } + (mazo?.userId ?: "")).distinct()
                val usuarios = autoresIds.mapNotNull { userId ->
                    getUserByIdUseCase(userId)
                }.associateBy { it.id }

                comentarios = comentarios.map { comentario ->
                    comentario.copy(autor = usuarios[comentario.autorId] ?: comentario.autor)
                }

                comentarios = comentarios.map { comentario ->
                    cargarRespuestasRecursivas(comentario, usuarios)
                }


                _uiState.update {
                    it.copy(
                        mazo = mazo,
                        comentarios = comentarios,
                        currentUserId = currentUserId,
                        usuarios=usuarios,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun cargarRespuestasRecursivas(
        comentario: Comentario,
        usuarios: Map<String, User>
    ): Comentario {
        val respuestas = getComentariosUseCase.getRespuestas(comentario.id).map { respuesta ->
            respuesta.copy(autor = usuarios[respuesta.autorId] ?: respuesta.autor)
        }

        return comentario.copy(
            respuestas = respuestas.map { cargarRespuestasRecursivas(it, usuarios) }
        )
    }

    fun actualizarTextoComentario(texto: String) {
        _uiState.update { it.copy(nuevoComentario = texto) }
    }

    fun enviarComentario() {
        val texto = _uiState.value.nuevoComentario.trim()
        if (texto.isBlank()) return

        viewModelScope.launch {
            try {
                val usuarioActual = getCurrentUserUseCase()
                val nuevoComentario = Comentario(
                    id = "com_${System.currentTimeMillis()}",
                    mazoId = mazoId,
                    autorId = currentUserId,
                    autor = usuarioActual ?: User(
                        id = currentUserId,
                        username = "",
                        email = ""
                    ),
                    contenido = texto,
                    likes = 0,
                    timestamp = System.currentTimeMillis(),
                    comentarioPadreId = _uiState.value.comentarioRespondiendoId
                )

                agregarComentarioUseCase(nuevoComentario)
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
        if (currentUserId.isBlank()) return
        viewModelScope.launch {
            try {
                toggleLikeComentarioUseCase(comentarioId,currentUserId)
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