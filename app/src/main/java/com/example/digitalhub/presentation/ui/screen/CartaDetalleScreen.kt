package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.CartaDetalleViewModelFactory
import com.example.digitalhub.presentation.ui.components.CartaDetalleContentido
import com.example.digitalhub.presentation.viewmodel.CartaDetalleViewModel

@Composable
fun CartaDetalleScreen(
    cartaId: String,
    viewModel: CartaDetalleViewModel = viewModel(
        factory = CartaDetalleViewModelFactory(cartaId)
    ),
    onBack:() -> Unit,
    onAddToDeck:() -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartaDetalleContentido(
        uiState = uiState,
        onBack = onBack,
        onToggleFavorita = viewModel::toggleFavorita,
        onIncrementarCantidad = viewModel::incrementarCantidad,
        onDecrementarCantidad = viewModel::decrementarCantidad,
        onAddToDeck = onAddToDeck
    )
}