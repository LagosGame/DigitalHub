package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.BibliotecaViewModelFactory
import com.example.digitalhub.presentation.ui.components.BibliotecaContentido
import com.example.digitalhub.presentation.viewmodel.BibliotecaViewModel

@Composable
fun BibliotecaScreen(
    viewModel: BibliotecaViewModel = viewModel(factory = BibliotecaViewModelFactory()),
    onBack: () -> Unit,
    onCartaClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BibliotecaContentido(
        uiState = uiState,
        onBack = onBack,
        onCartaClick = onCartaClick,
        onAbrirSelector = viewModel::abrirSelector,
        onSeleccionarColor = viewModel::selectColor,
        onSeleccionarCoste = viewModel::selectCoste,
        onSeleccionarRareza = viewModel::selectRareza,
        onSeleccionarTipo = viewModel::selectTipo,
        onSeleccionarNivel = viewModel::selectNivel,
        onSeleccionarExpansion = viewModel::selectExpansion,
        onActivarFavoritas = viewModel::activarFav,
        onActivarAlternativas = viewModel::activarAlt,
        onActivarSoloMiBiblioteca = viewModel::activarSoloMiBiblioteca,
        onBusquedaChange = viewModel::onBusquedaChange,
        onLimpiarFiltros = viewModel::limpiarFiltros
    )
}
@Preview
@Composable
fun BibliotecaPreview(
){
    BibliotecaScreen(
        onBack = {}
    ) { }
}