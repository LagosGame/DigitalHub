package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.VerMazoViewModelFactory
import com.example.digitalhub.presentation.ui.components.VerMazoContentido
import com.example.digitalhub.presentation.viewmodel.VerMazoViewModel

@Composable
fun VerMazoScreen(
    mazoId: String,
    viewModel: VerMazoViewModel = viewModel(factory = VerMazoViewModelFactory(mazoId)),
    onBack: () -> Unit,
    onPerfilAutor: (String) -> Unit,
    onComentariosClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mostrarDialogoCopiar by viewModel.mostrarDialogoCopiar.collectAsStateWithLifecycle()

    VerMazoContentido(
        uiState = uiState,
        mostrarDialogoCopiar = mostrarDialogoCopiar,
        onBack = onBack,
        onPerfilAutor = onPerfilAutor,
        onAbrirDialogoCopiar = viewModel::abrirDialogoCopiar,
        onConfirmarCopia = viewModel::confirmarCopia,
        onCancelarCopia = viewModel::cerrarDialogoCopiar,
        onComentariosClick = onComentariosClick
    )
}