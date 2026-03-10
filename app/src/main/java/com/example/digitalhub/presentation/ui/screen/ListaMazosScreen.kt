package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.ListaMazosViewModelFactory
import com.example.digitalhub.presentation.ui.components.ListaMazosContentido
import com.example.digitalhub.presentation.viewmodel.ListaMazosViewModel

@Composable
fun ListaMazosScreen(
    viewModel: ListaMazosViewModel = viewModel(factory = ListaMazosViewModelFactory()),
    onBack: () -> Unit,
    onMetaClick: () -> Unit,
    onMazoClick: (String) -> Unit,
    onPerfilClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mazoACopiar by viewModel.mazoACopiar.collectAsStateWithLifecycle()

    ListaMazosContentido(
        uiState = uiState,
        mazoACopiar = mazoACopiar,
        onBack = onBack,
        onMetaClick = onMetaClick,
        onBusquedaChange = viewModel::actualizarBusqueda,
        onMazoClick = onMazoClick,
        onPerfilClick = onPerfilClick,
        onCopiarMazo = viewModel::mostrarDialogoCopiar,
        onConfirmarCopia = viewModel::confirmarCopia,
        onCancelarCopia = viewModel::ocultarDialogoCopiar
    )
}