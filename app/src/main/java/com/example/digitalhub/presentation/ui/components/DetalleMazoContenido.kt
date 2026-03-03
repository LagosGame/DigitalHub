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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.DetalleMazoUiState
import com.example.digitalhub.ui.theme.DigitalFont
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun DetalleMazoContentido(
    uiState: DetalleMazoUiState,
    onBack: () -> Unit,
    onGuardar: () -> Unit,
    onEditarDescripcion: () -> Unit,
    onAñadirEstrategia: () -> Unit,
    onEliminarEstrategia: (Int) -> Unit,
    onAñadirCartaImportante: () -> Unit,
    onEliminarCartaImportante: (String) -> Unit,
    onAñadirTag: () -> Unit,
    onEliminarTag: (String) -> Unit,
    onEditarEstadisticas: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        FondoPrincipal()
        when{
            uiState.isLoading ->{
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            uiState.errorMessage !=null ->{
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.mazo != null ->{
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1565C0))
                            .padding(vertical = 20.dp)
                    ) {
                        BotonX(onBack)

                        Text(
                            text = "GUIDES & STRATEGIES",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = DigitalFont,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    )
                    {
                        SeccionDescripcion(
                            titulo = "Deck description",
                            contenido = uiState.descripcion,
                            onEdit = onEditarDescripcion
                        )
                        SeccionEstrategias(estrategias = uiState.estrategias,
                            todasLasCartas = uiState.todasLasCartas,
                            onAñadir = onAñadirEstrategia,
                            onEliminar = onEliminarEstrategia
                            )
                        val cartasImportantesItems = uiState.todasLasCartas.filter {
                            it.id in uiState.cartasImportantes
                        }
                        SeccionCartasyTags(
                            cartasImportantes = cartasImportantesItems,
                            tags = uiState.tags,
                            onAñadirCarta = onAñadirCartaImportante,
                            onEliminarCarta = onEliminarCartaImportante,
                            onAñadirTag = onAñadirTag,
                            onEliminarTag = onEliminarTag
                        )
                        SeccionEstadisticas(
                            estadisticas = uiState.estadisticas,
                            onEdit = onEditarEstadisticas
                        )

                        BotonMazo(
                            tipo = TipoBoton.EDITAR,
                            texto = "Guardar",
                            onClick = onGuardar
                        )

                        Spacer(Modifier.height(32.dp))
                    }
                }
            }
        }
        //Todo vacio porque lo hac eel viewmodel//
        if (uiState.mostrarDialogoDescripcion) {
            DialogoEditarDescripcion(
                descripcionActual = uiState.descripcion,
                onConfirm = {},
                onDismiss = {}
            )
        }

        if (uiState.mostrarDialogoEstrategia) {
            val cartasDelMazo = uiState.mazo?.cartas?.mapNotNull { cartaEnMazo ->
                uiState.todasLasCartas.find { it.id == cartaEnMazo.cartaId }
            } ?: emptyList()

            DialogoAñadirEstrategia(
                cartasDelMazo = cartasDelMazo,
                onConfirm = { titulo, cartasIds ->

                },
                onDismiss = {}
            )
        }

        if (uiState.mostrarDialogoCartasImportantes) {
            val cartasDelMazo = uiState.mazo?.cartas?.mapNotNull { cartaEnMazo ->
                uiState.todasLasCartas.find { it.id == cartaEnMazo.cartaId }
            } ?: emptyList()

            DialogoElegirPortada(
                cartas = cartasDelMazo,
                onCartaSeleccionada = { carta ->
                },
                onDismiss = {}
            )
        }

        if (uiState.mostrarDialogoTags) {
            DialogoAñadirTag(
                onConfirm = {},
                onDismiss = {}
            )
        }

        if (uiState.mostrarDialogoEstadisticas) {
            DialogoEditarEstadisticas(
                estadisticasActuales = uiState.estadisticas,
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}