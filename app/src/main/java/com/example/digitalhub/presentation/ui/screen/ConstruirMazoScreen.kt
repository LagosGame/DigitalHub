package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.ConstruirMazoViewModelFactory
import com.example.digitalhub.presentation.ui.components.ConstruirMazoContenido
import com.example.digitalhub.presentation.viewmodel.ConstruirMazoViewModel

@Composable
fun ConstruirMazoScreen(
    viewModel: ConstruirMazoViewModel = viewModel(factory = ConstruirMazoViewModelFactory()),
    onBack: () -> Unit,
    onCrearMazo: () -> Unit,
    onMazoClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mazoAEliminar by viewModel.mazoAEliminar.collectAsStateWithLifecycle()

    ConstruirMazoContenido(
        uiState = uiState,
        mazoAEliminar= mazoAEliminar,
        onBack = onBack,
        onCrearMazo = onCrearMazo,
        onMazoClick = onMazoClick,
        onEliminarMazo = viewModel::mostrarDialogoEliminar,
        onConfirmarEliminacion = viewModel::confirmarEliminacion,
        onCancelarEliminacion = viewModel::ocultarDialogoEliminar
    )
}