package com.example.digitalhub.presentation.ui.components

import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onEstablecerPortada: (String) -> Unit,
    onToggleFavorito: () -> Unit,
    onNavToDetalle: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
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
                onNavToDetalle = onNavToDetalle,
                onToggleFavorito = onToggleFavorito,
                modifier = Modifier.weight(1f)
            )
            ZonaBiblioteca(
                cartas = uiState.cartasBiblioteca,
                isLoading = uiState.isLoadingBiblioteca,
                onCartaClick = onAñadirCarta,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
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
                onNavToDetalle = onNavToDetalle,
                onToggleFavorito = onToggleFavorito,
                modifier = Modifier.weight(1f)
            )
            ZonaBiblioteca(
                cartas = uiState.cartasBiblioteca,
                isLoading = uiState.isLoadingBiblioteca,
                onCartaClick = onAñadirCarta,
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (mostrarDialogoPortada) {
        if (cartasDelMazo.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add cards to the deck first to choose a cover",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            onCerrarDialogoPortada()
        } else {
            DialogoElegirPortada(
                cartas = cartasDelMazo,
                onCartaSeleccionada = { carta ->
                    onEstablecerPortada(carta.id)
                    onCerrarDialogoPortada()
                },
                onDismiss = onCerrarDialogoPortada
            )
        }
    }
}