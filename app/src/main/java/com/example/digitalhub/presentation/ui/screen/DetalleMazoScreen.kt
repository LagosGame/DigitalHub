package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.DetalleMazoViewModelFactory
import com.example.digitalhub.presentation.ui.components.DetalleMazoContentido
import com.example.digitalhub.presentation.viewmodel.DetalleMazoViewModel

@Composable
fun DetalleMazoScreen(
    mazoId: String,
    viewModel: DetalleMazoViewModel = viewModel(
        factory = DetalleMazoViewModelFactory(mazoId)
    ),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetalleMazoContentido(
        uiState = uiState,
        onBack = onBack,
        onGuardar = {
            viewModel.guardarCambios {
                onBack()
            }
        },
        onEditarDescripcion = viewModel::toggleDialogoDescripcion,
        onAñadirEstrategia = viewModel::toggleDialogoEstrategia,
        onEliminarEstrategia = viewModel::eliminarEstrategia,
        onAñadirCartaImportante = viewModel::toggleDialogoCartasImportantes,
        onEliminarCartaImportante = viewModel::eliminarCartaImportante,
        onAñadirTag = viewModel::toggleDialogoTags,
        onEliminarTag = viewModel::eliminarTag,
        onEditarEstadisticas = viewModel::toggleDialogoEstadisticas
    )

    //Diálogos
    if (uiState.mostrarDialogoDescripcion) {
        com.example.digitalhub.presentation.ui.components.DialogoEditarDescripcion(
            descripcionActual = uiState.descripcion,
            onConfirm = { nuevaDescripcion ->
                viewModel.actualizarDescripcion(nuevaDescripcion)
            },
            onDismiss = viewModel::toggleDialogoDescripcion
        )
    }

    if (uiState.mostrarDialogoEstrategia) {
        val cartasDelMazo = uiState.mazo?.cartas?.mapNotNull { cartaEnMazo ->
            uiState.todasLasCartas.find { it.id == cartaEnMazo.cartaId }
        } ?: emptyList()

        com.example.digitalhub.presentation.ui.components.DialogoAñadirEstrategia(
            cartasDelMazo = cartasDelMazo,
            onConfirm = { titulo, cartasIds ->
                viewModel.añadirEstrategia(titulo, cartasIds)
            },
            onDismiss = viewModel::toggleDialogoEstrategia
        )
    }

    if (uiState.mostrarDialogoCartasImportantes) {
        val cartasDelMazo = uiState.mazo?.cartas?.mapNotNull { cartaEnMazo ->
            uiState.todasLasCartas.find { it.id == cartaEnMazo.cartaId }
        } ?: emptyList()

        com.example.digitalhub.presentation.ui.components.DialogoElegirPortada(
            cartas = cartasDelMazo,
            onCartaSeleccionada = { carta ->
                viewModel.añadirCartaImportante(carta.id)
            },
            onDismiss = viewModel::toggleDialogoCartasImportantes
        )
    }

    if (uiState.mostrarDialogoTags) {
        com.example.digitalhub.presentation.ui.components.DialogoAñadirTag(
            onConfirm = { tag ->
                viewModel.añadirTag(tag)
            },
            onDismiss = viewModel::toggleDialogoTags
        )
    }

    if (uiState.mostrarDialogoEstadisticas) {
        com.example.digitalhub.presentation.ui.components.DialogoEditarEstadisticas(
            estadisticasActuales = uiState.estadisticas,
            onConfirm = { nuevasStats ->
                viewModel.actualizarEstadistica("Attack", nuevasStats.ataque)
                viewModel.actualizarEstadistica("Defense", nuevasStats.defensa)
                viewModel.actualizarEstadistica("Consistency", nuevasStats.consistencia)
                viewModel.actualizarEstadistica("Versatility", nuevasStats.versatilidad)
                viewModel.actualizarEstadistica("Comeback", nuevasStats.recuperacion)
            },
            onDismiss = viewModel::toggleDialogoEstadisticas
        )
    }
}