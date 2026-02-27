package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.presentation.ui.state.CrearMazoUiState
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun ZonaMazo(
    uiState: CrearMazoUiState,
    cartasDelMazo: List<Carta>,
    cartaPortada: Carta?,
    onNombreChange: (String) -> Unit,
    onQuitarCarta: (String, Boolean) -> Unit,
    onBack: () -> Unit,
    onGuardar: () -> Unit,
    onLimpiar: () -> Unit,
    onElegirPortada: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = uiState.nombreMazo,
                onValueChange = onNombreChange,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
                    .border(width = 3.dp, color = Color.Black),
                singleLine = true,
                placeholder = { Text("Deck name...") }
            )

            // Contador
            LetrasBordes(
                text = "${uiState.totalNormales}/50 + ${uiState.totalHuevo}/5",
                fontSize = 20.sp,
                fontFamily = Kenyan,
                fontWeight = FontWeight.Normal,
                textColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 5f,
                textAlign = TextAlign.Center
            )

            BotonX(onBack)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (cartasDelMazo.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add cards from the library",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            GridMazo(
                cartas = cartasDelMazo,
                obtenerCantidad = { cartaId ->
                    val normales = uiState.cartasNormales
                        .filter { it.cartaId == cartaId }
                        .sumOf { it.cantidad }
                    val huevos = uiState.cartasHuevo
                        .filter { it.cartaId == cartaId }
                        .sumOf { it.cantidad }
                    normales + huevos
                },
                onCartaClick = { carta ->
                    val esHuevo = carta.tipo == com.example.digitalhub.domain.model.TipoCarta.DIGIEGG
                    onQuitarCarta(carta.id, esHuevo)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotonMazo(
                    tipo = TipoBoton.EDITAR,
                    onClick = onGuardar,
                    texto = "Save"
                )

                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, null, tint = Color.Red)
                }

                IconButton(onClick = {}) {
                    Icon(Icons.Default.Info, null)
                }

                // Portada
                Box(
                    modifier = Modifier.clickable { onElegirPortada() }
                ) {
                    if (cartaPortada != null) {
                        CartaPreviewDeck(
                            imagenId = cartaPortada.imagenId,
                            borderColor = Color.Blue
                        )
                    } else {
                        IconButton(onClick = onElegirPortada) {
                            Icon(
                                Icons.Default.Face,
                                contentDescription = "Elegir portada",
                                tint = Color.Gray
                            )
                        }
                    }
                }

                IconButton(onClick = onLimpiar) {
                    Icon(Icons.Default.Delete, null, tint = Color.Red)
                }
            }

        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp)
            )
         }
      }
    }
}