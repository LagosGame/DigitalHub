package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun CartaDetalleBoxes(
    carta: Carta,
    onBack: () -> Unit,
    onToggleFavorita: () -> Unit,
    onIncrementarCantidad: () -> Unit,
    onDecrementarCantidad: () -> Unit,
    onAddToDeck: () -> Unit

) {
    val estilo = carta.color.firstOrNull() ?: ColorCarta.RAINBOW
    Column(modifier = Modifier.fillMaxSize()) {
        //Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonX(onBack)

            IconButton(onClick = onToggleFavorita) {
                Icon(
                    imageVector = if (carta.esFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorita",
                    tint = if (carta.esFav) Color.Red else Color.Gray,
                    modifier = Modifier.scale(1.5f)
                )
            }
        }

        //Imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(carta.imagenId),
                contentDescription = carta.nombre,
                modifier = Modifier.fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
                // Blandca y negra si no la tienes
                colorFilter = if (carta.cantidadEnBiblioteca == 0) {
                    ColorFilter.colorMatrix(
                        ColorMatrix().apply { setToSaturation(0f) }
                    )
                } else {
                    null
                }
            )
        }

        //Info
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //Nombre e ID
                BoxInfoCarta(
                    colorCarta = estilo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "${carta.nombre} | ${carta.id} | ${carta.rareza.letra}",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                //Coste,Evo,DP
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BoxInfoCarta(
                        colorCarta = estilo,
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                    ) {
                        Text(
                            text = "Cost - ${carta.coste}",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    BoxInfoCarta(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        colorCarta = estilo
                    ) {
                        Text(
                            text = "Evo - ${carta.costeEvolucion ?: "-"}",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    BoxInfoCarta(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        colorCarta = estilo
                    ) {
                        Text(
                            text = "DP - ${carta.dp ?: "-"}",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                //Nivel y Atributo
                BoxInfoCarta(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colorCarta = estilo
                ) {
                    Text(
                        text = buildString {
                            append(carta.nivel?.etiqueta ?: "N/A")
                            if (carta.rango != null) {
                                append(" | ${carta.rango}")
                            }
                            if (carta.atributo != null) {
                                append(" | ${carta.atributo}")
                            }
                            if (carta.trait != null) {
                                append(" | ${carta.trait}")
                            }
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }

                //Texto/Efecto
                if (!carta.texto.isNullOrBlank()) {
                    BoxInfoCarta(
                        modifier = Modifier.fillMaxWidth(),
                        colorCarta = estilo
                    ) {
                        Text(
                            text = carta.texto,
                            textAlign = TextAlign.Justify,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                //Cantidad
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //"Añadir a mazo"
                    BotonMazo(
                        tipo = TipoBoton.COLOR_CARTA,
                        colorCarta = carta.color.firstOrNull(),
                        texto = "Add to deck",
                        enabled = carta.cantidadEnBiblioteca > 0,
                        onClick = onAddToDeck
                    )

                    // Box con control de biblioteca
                    BoxInfoCarta(
                        colorCarta = estilo,
                        modifier = Modifier
                            .width(220.dp)
                            .height(50.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Add to Library",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )

                            //
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //
                                IconButton(
                                    onClick = onDecrementarCantidad,
                                    enabled = carta.cantidadEnBiblioteca > 0,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = "-",
                                        tint = if (carta.cantidadEnBiblioteca > 0) Color.White else Color.Gray,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                //Cantidad
                                Text(
                                    text = "${carta.cantidadEnBiblioteca}",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                //
                                IconButton(
                                    onClick = onIncrementarCantidad,
                                    enabled = carta.cantidadEnBiblioteca < 4,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowUp,
                                        contentDescription = "+",
                                        tint = if (carta.cantidadEnBiblioteca < 4) Color.White else Color.Gray,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}