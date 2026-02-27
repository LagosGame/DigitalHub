package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.presentation.ui.state.CrearMazoUiState

@Composable
fun CrearMazoContentido(
    uiState: CrearMazoUiState,
    cartasDelMazo: List<Carta>,
    cartaPortada: Carta?,
    mostrarDialogoPortada: Boolean,
    onNombreChange: (String) -> Unit,
    onAñadirCarta: (Carta) -> Unit,
    onQuitarCarta: (String, Boolean) -> Unit,
    onBack: () -> Unit,
    onGuardar: () -> Unit,
    onLimpiar: () -> Unit,
    onAbrirDialogoPortada: () -> Unit,
    onCerrarDialogoPortada: () -> Unit,
    onEstablecerPortada: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ZonaMazo(
            uiState = uiState,
            cartasDelMazo = cartasDelMazo,
            cartaPortada = cartaPortada,
            onNombreChange = onNombreChange,
            onQuitarCarta = onQuitarCarta,
            onBack = onBack,
            onGuardar = onGuardar,
            onLimpiar = onLimpiar,
            onElegirPortada = onAbrirDialogoPortada,
            modifier = Modifier.weight(1f)
        )

        ZonaBiblioteca(
            cartas = uiState.cartasBiblioteca,
            isLoading = uiState.isLoadingBiblioteca,
            onCartaClick = onAñadirCarta,
            modifier = Modifier.weight(1f)
        )
    }

    if (mostrarDialogoPortada) {
        DialogoElegirPortada(
            cartas = cartasDelMazo,
            onCartaSeleccionada = { carta ->
                onEstablecerPortada(carta.id)
            },
            onDismiss = onCerrarDialogoPortada
        )
    }
}