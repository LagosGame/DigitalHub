package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    onBusquedaChange: (String) -> Unit,
    onLimpiarFiltros:()-> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            BotonX(onBack)
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
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}