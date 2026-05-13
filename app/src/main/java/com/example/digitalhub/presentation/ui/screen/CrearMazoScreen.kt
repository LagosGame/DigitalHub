package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.CrearMazoViewModelFactory
import com.example.digitalhub.presentation.ui.components.CrearMazoContentido
import com.example.digitalhub.presentation.viewmodel.CrearMazoViewModel

@Composable
fun CrearMazoScreen(
    mazoId: String? = null,
    viewModel: CrearMazoViewModel = viewModel(factory = CrearMazoViewModelFactory(mazoId)),
    onBack: () -> Unit,
    onNavToDetalle: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventoNavegacion by viewModel.eventoNavegacion.collectAsStateWithLifecycle()

    LaunchedEffect(eventoNavegacion) {
        when (eventoNavegacion) {
            is CrearMazoViewModel.EventoNavegacion.VolverAtras -> {
                onBack()
                viewModel.limpiarEvento()
            }
            null -> {}
        }
    }

    val cartasDelMazo = remember(uiState.cartasNormales, uiState.cartasHuevo, uiState.cartasBiblioteca) {
        val todasIds = (uiState.cartasNormales + uiState.cartasHuevo).map { it.cartaId }.distinct()
        uiState.cartasBiblioteca.filter { todasIds.contains(it.id) }
    }

    val cartaPortada = remember(uiState.portadaId, uiState.cartasBiblioteca) {
        uiState.cartasBiblioteca.find { it.id == uiState.portadaId }
    }

    var mostrarDialogoPortada by remember { mutableStateOf(false) }

    CrearMazoContentido(
        uiState = uiState,
        cartasDelMazo = cartasDelMazo,
        cartaPortada = cartaPortada,
        mostrarDialogoPortada = mostrarDialogoPortada,
        onNombreChange = viewModel::onNombreChange,
        onAñadirCarta = viewModel::añadirCarta,
        onQuitarCarta = viewModel::quitarCarta,
        onBack = onBack,
        onGuardar = {
            viewModel.guardarMazo(onSuccess = {})
        },
        onLimpiar = viewModel::limpiar,
        onAbrirDialogoPortada = { mostrarDialogoPortada = true },
        onCerrarDialogoPortada = { mostrarDialogoPortada = false },
        onEstablecerPortada = viewModel::establecerPortada,
        onToggleFavorito = viewModel::toggleFavorito,
        onNavToDetalle = {
            val id = mazoId ?: uiState.mazoId
            if (id.isNotBlank()) {
                onNavToDetalle(uiState.mazoId)
            }
        }
    )
}