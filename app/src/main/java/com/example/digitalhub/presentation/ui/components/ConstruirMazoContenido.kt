package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.presentation.ui.state.ConstruirMazoUiState
import com.example.digitalhub.ui.theme.Roboto

@Composable
fun ConstruirMazoContenido(
    uiState: ConstruirMazoUiState,
    onBack:()-> Unit,
    onCrearMazo:()-> Unit,
    onMazoClick:(String)-> Unit,
    onEditarMazo:(String)-> Unit,
    onEliminarMazo:(String)-> Unit,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        FondoPrincipal()
        Column(modifier = Modifier.fillMaxSize())
        {
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
                                        Text(
                                            text = mazo.nombre,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        IndicadorColorMazo(mazo.colores)
                                    }

                                    //Preview carta + Cantidad + Acciones
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        CartaPreviewDeck(imagenId = mazo.portadaId)

                                        Text(
                                            text = "${mazo.cartasNormales} / ${mazo.cartasHuevo}",
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )

                                        AccionesDeck(
                                            onEdit = { onEditarMazo(mazo.id) },
                                            onDelete = { onEliminarMazo(mazo.id) }
                                        )
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