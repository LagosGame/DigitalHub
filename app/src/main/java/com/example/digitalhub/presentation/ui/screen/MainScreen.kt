package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.MainViewModelFactory
import com.example.digitalhub.presentation.ui.components.HomeContentido
import com.example.digitalhub.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = MainViewModelFactory()),
    onBiblioteca: () -> Unit,
    onConstruir: () -> Unit,
    onLista: () -> Unit,
    onPerfil: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContentido(
        uiState = uiState,
        onCambiarIndice = viewModel::setIndice,
        onBiblioteca = onBiblioteca,
        onConstruir = onConstruir,
        onLista = onLista,
        onPerfil = onPerfil,
        onReportarError = viewModel::abrirDialogoReportar,
        onMensajeChange = viewModel::actualizarMensajeError,
        onEnviarReporte = viewModel::enviarReporte,
        onCerrarDialogo = viewModel::cerrarDialogoReportar
    )
}