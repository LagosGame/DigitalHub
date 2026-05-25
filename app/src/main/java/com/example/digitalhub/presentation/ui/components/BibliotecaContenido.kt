package com.example.digitalhub.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.digitalhub.domain.model.*
import com.example.digitalhub.presentation.ui.state.BibliotecaUiState
import com.example.digitalhub.presentation.ui.state.Selector

@Composable
fun BibliotecaContentido(
    uiState: BibliotecaUiState,
    onBack: () -> Unit,
    onCartaClick: (String) -> Unit,
    onAbrirSelector: (Selector) -> Unit,
    onSeleccionarColor: (ColorCarta?) -> Unit,
    onSeleccionarCoste: (Int?) -> Unit,
    onSeleccionarRareza: (RarezaCarta?) -> Unit,
    onSeleccionarTipo: (TipoCarta?) -> Unit,
    onSeleccionarNivel: (Nivel?) -> Unit,
    onSeleccionarExpansion: (Expansion?) -> Unit,
    onActivarFavoritas: () -> Unit,
    onActivarAlternativas: () -> Unit,
    onActivarSoloMiBiblioteca: () -> Unit,
    onBusquedaChange: (String) -> Unit,
    onLimpiarFiltros:()-> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()
        if (isLandscape) {

            Row(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Filtros(
                        uiState = uiState,
                        onAbrirSelector = onAbrirSelector,
                        onSeleccionarColor = onSeleccionarColor,
                        onSeleccionarCoste = onSeleccionarCoste,
                        onSeleccionarRareza = onSeleccionarRareza,
                        onSeleccionarTipo = onSeleccionarTipo,
                        onSeleccionarNivel = onSeleccionarNivel,
                        onSeleccionarExpansion = onSeleccionarExpansion,
                        onLimpiarFiltros = onLimpiarFiltros
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BottomBarBiblioteca(
                        uiState = uiState,
                        onBusquedaChange = onBusquedaChange,
                        onActivarFavoritas = onActivarFavoritas,
                        onActivarAlternativas = onActivarAlternativas,
                        onActivarSoloMiBiblioteca = onActivarSoloMiBiblioteca
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    when {
                        uiState.isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator(color = Color.White) }
                        }
                        uiState.errorMessage != null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = uiState.errorMessage,
                                    color = Color.Red,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        else -> {
                            GridBiblioteca(
                                cartas = uiState.cartas,
                                columnas = 6,
                                bottomPadding = 8,
                                modifier = Modifier.padding(top = 8.dp),
                                onCartaClick = { carta -> onCartaClick(carta.id) }
                            )
                        }
                    }
                }
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Filtros(
                    uiState = uiState,
                    onAbrirSelector = onAbrirSelector,
                    onSeleccionarColor = onSeleccionarColor,
                    onSeleccionarCoste = onSeleccionarCoste,
                    onSeleccionarRareza = onSeleccionarRareza,
                    onSeleccionarTipo = onSeleccionarTipo,
                    onSeleccionarNivel = onSeleccionarNivel,
                    onSeleccionarExpansion = onSeleccionarExpansion,
                    onLimpiarFiltros = onLimpiarFiltros
                )
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                } else if (uiState.errorMessage != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    GridBiblioteca(
                        cartas = uiState.cartas,
                        modifier = Modifier.padding(top = 40.dp),
                        onCartaClick = { carta ->
                            onCartaClick(carta.id)
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .align(Alignment.BottomCenter)
            ) {
                BottomBarBiblioteca(
                    uiState = uiState,
                    onBusquedaChange = onBusquedaChange,
                    onActivarFavoritas = onActivarFavoritas,
                    onActivarAlternativas = onActivarAlternativas,
                    onActivarSoloMiBiblioteca=onActivarSoloMiBiblioteca,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}
