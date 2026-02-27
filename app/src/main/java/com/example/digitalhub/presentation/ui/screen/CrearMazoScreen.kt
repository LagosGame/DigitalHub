package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.CrearMazoViewModelFactory
import com.example.digitalhub.presentation.ui.components.CrearMazoContentido
import com.example.digitalhub.presentation.viewmodel.CrearMazoViewModel

@Composable
fun CrearMazoScreen(
    viewModel: CrearMazoViewModel = viewModel(factory = CrearMazoViewModelFactory()),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mostrarDialogoPortada by viewModel.mostrarDialogoPortada.collectAsStateWithLifecycle()

    //Obtener cartas completas del mazo
    val cartasDelMazo by remember {
        derivedStateOf {
            val todasLasCartasEnMazo = uiState.cartasNormales + uiState.cartasHuevo

            //Agrupar por cartaId y contar cantidad total
            todasLasCartasEnMazo
                .groupBy { it.cartaId }
                .mapNotNull { (cartaId, listaCartaEnMazo) ->
                    val carta = uiState.cartasBiblioteca.find { it.id == cartaId }
                    carta?.let {
                        it to listaCartaEnMazo.sumOf { c -> c.cantidad }
                    }
                }
                .map { it.first }
        }
    }

    val cartaPortada by remember {
        derivedStateOf {
            uiState.cartaIdPortada?.let { portadaId ->
                uiState.cartasBiblioteca.find { it.id == portadaId }
            }
        }
    }

    CrearMazoContentido(
        uiState = uiState,
        cartasDelMazo = cartasDelMazo,
        cartaPortada = cartaPortada,
        mostrarDialogoPortada = mostrarDialogoPortada,
        onNombreChange = viewModel::cambiarNombreMazo,
        onAñadirCarta = viewModel::añadirCarta,
        onQuitarCarta = viewModel::quitarCarta,
        onBack = onBack,
        onGuardar = {
            viewModel.guardarMazo {
                onBack()
            }
        },
        onLimpiar = viewModel::limpiarMazo,
        onAbrirDialogoPortada = viewModel::abrirDialogoPortada,
        onCerrarDialogoPortada = viewModel::cerrarDialogoPortada,
        onEstablecerPortada = viewModel::establecerPortada
    )
}