package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.presentation.ui.state.ConstruirMazoUiState
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.Roboto

@Composable
fun ConstruirMazoContenido(
    uiState: ConstruirMazoUiState,
    onBack:()-> Unit,
    onCrearMazo:()-> Unit,
    mazoAEliminar: String?,
    onMazoClick:(String)-> Unit,
    onEliminarMazo:(String)-> Unit,
    onConfirmarEliminacion: () -> Unit,
    onCancelarEliminacion: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        FondoPrincipal()
        Column(modifier = Modifier.fillMaxSize()
            .padding(12.dp))
        {
            Spacer(modifier = Modifier.height(20.dp))
            BotonX(onBack)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                BotonesDentro(
                    text = "CREATE DECK",
                    fontSize = 24.sp,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .height(100.dp)
                        .width(350.dp),
                    isEnabled = true,
                    onClick = onCrearMazo
                )
            }

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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                uiState.mazos.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You don't have any decks. Create one!",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.mazos) { mazo ->
                            val colorMazo = mazo.colores.firstOrNull() ?: ColorCarta.RAINBOW

                            val esBlanco = colorMazo == ColorCarta.WHITE
                            val colorTexto = if (esBlanco) Color.Black else Color.White
                            val colorBorde = if (esBlanco) Color.Black else Color.White

                            BoxInfoCarta(colorCarta = colorMazo) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onMazoClick(mazo.id) }
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    //Nombre + Indicador de colores
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                        LetrasBordes(
                                            text = mazo.nombre,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = Kenyan,
                                            textColor = colorTexto,
                                            strokeColor = Color.Black,
                                            strokeWidth = 20f,
                                            textAlign = TextAlign.Center
                                        )
                                            if (mazo.esFavorito) {
                                                Icon(
                                                    Icons.Default.Favorite,
                                                    contentDescription = "Favorito",
                                                    tint = Color.Red,
                                                    modifier = Modifier
                                                        .padding(top = 8.dp, end = 8.dp)
                                                        .size(32.dp)
                                                        .border(2.dp, Color.Black, CircleShape)
                                                        .background(Color.White, CircleShape)
                                                        .padding(4.dp)
                                                )
                                            }
                                        }
                                        IndicadorColorMazo(mazo.colores)
                                    }

                                    //Preview carta + Cantidad + Acciones
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        CartaPreviewDeck(imagenId = mazo.portadaId,
                                            borderColor = colorBorde)

                                        Text(
                                            text = "${mazo.cartasNormales} / ${mazo.cartasHuevo}",
                                            fontSize = 14.sp,
                                            color = colorTexto
                                        )

                                        AccionesDeck(
                                            onEdit = { onMazoClick(mazo.id) },
                                            onDelete = { onEliminarMazo(mazo.id) }
                                        )
                                    }
                                    //mensahe
                                    if (mazoAEliminar != null) {
                                        val mazo = uiState.mazos.find { it.id == mazoAEliminar }
                                        if (mazo != null) {
                                            val cartasDelMazo = uiState.todasLasCartas?.filter { carta ->
                                                mazo.cartas.any { it.cartaId == carta.id }
                                            } ?: emptyList()

                                            DialogoEliminarMazo(
                                                mazo = mazo,
                                                cartasDelMazo = cartasDelMazo,
                                                onConfirmar = onConfirmarEliminacion,
                                                onCancelar = onCancelarEliminacion
                                            )
                                        }
                                    }
                                    //Tags
                                    if (mazo.tags.isNotEmpty()) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            mazo.tags.forEach { tag ->
                                                TagsDeck(tag)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}