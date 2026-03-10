package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.estiloParaColor

@Composable
fun DialogoCopiarMazo(
    mazo: Mazo,
    cartasDelMazo: List<Carta>,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    val colorMazo = mazo.colores.firstOrNull() ?: ColorCarta.RAINBOW
    val estilo = estiloParaColor(colorMazo)

    AlertDialog(
        onDismissRequest = onCancelar,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(16.dp),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Do you want to ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "COPY",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "this deck?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(estilo.fondo, RoundedCornerShape(12.dp))
                        .border(3.dp, estilo.borde, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CartaPreviewDeck(
                                imagenId = mazo.portadaId,
                                borderColor = estilo.borde
                            )

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = mazo.nombre,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = estilo.texto
                                    )


                                    IndicadorColorMazo(mazo.colores)
                                }

                                Text(
                                    text = "${mazo.cartasNormales}+${mazo.cartasHuevo}/${50}+${5}",
                                    fontSize = 16.sp,
                                    color = estilo.texto
                                )


                                if (mazo.tags.isNotEmpty()) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        mazo.tags.take(2).forEach { tag ->
                                            TagsDeck(tag)
                                        }
                                    }
                                }
                            }
                        }

                        if (mazo.esFavorito) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Favorito",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Preview",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(cartasDelMazo.take(12)) { carta ->
                            CartaPreviewDeck(
                                imagenId = carta.imagenId,
                                borderColor = Color.White
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            BotonMazo(
                tipo = TipoBoton.EDITAR,
                texto = "Copiar",
                onClick = onConfirmar
            )
        },
        dismissButton = {
            BotonMazo(
                tipo = TipoBoton.CANCELAR,
                texto = "Cancelar",
                onClick = onCancelar
            )
        },
        containerColor = Color(0xFF1565C0),
        shape = RoundedCornerShape(16.dp)
    )
}