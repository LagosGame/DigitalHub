package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.CartaDetalleUiState

@Composable
fun CartaDetalleContentido(
    uiState: CartaDetalleUiState,
    onBack: () -> Unit,
    onToggleFavorita: () -> Unit,
    onIncrementarCantidad: () -> Unit,
    onDecrementarCantidad: () -> Unit,
    onAddToDeck: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            uiState.carta != null -> {
                CartaDetalleBoxes(
                    carta = uiState.carta,
                    onBack = onBack,
                    onToggleFavorita = onToggleFavorita,
                    onIncrementarCantidad = onIncrementarCantidad,
                    onDecrementarCantidad = onDecrementarCantidad,
                    onAddToDeck = onAddToDeck
                )
            }
        }
    }
}