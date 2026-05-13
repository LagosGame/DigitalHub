package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.User

data class ComentariosUiState(
    val mazo: Mazo? = null,
    val comentarios: List<Comentario> = emptyList(),
    val currentUserId: String = "",
    val isLoading: Boolean = false,
    val usuarios: Map<String, User> = emptyMap(),
    val errorMessage: String? = null,
    val nuevoComentario: String = "",
    val comentarioRespondiendoId: String? = null,
    val mostrarDialogoComentar: Boolean = false,
    val mostrarDialogoResponder: Boolean = false
)
