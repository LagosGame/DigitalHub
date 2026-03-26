package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Comentario
import com.example.digitalhub.domain.model.Mazo

data class ComentariosUiState(
    val mazo: Mazo? = null,
    val comentarios: List<Comentario> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nuevoComentario: String = "",
    val comentarioRespondiendoId: String? = null,
    val mostrarDialogoComentar: Boolean = false,
    val mostrarDialogoResponder: Boolean = false
)
