package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun CardMazoLista(
    mazo: Mazo,
    onMazoClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onCopiarClick: () -> Unit
) {
    val colorMazo = mazo.colores.firstOrNull() ?: ColorCarta.RAINBOW
    val esBlanco = colorMazo == ColorCarta.WHITE
    val colorTexto = if (esBlanco) Color.Black else Color.White
    val colorBorde = if (esBlanco) Color.Black else Color.White

    Box(modifier = Modifier.fillMaxWidth()) {
        BoxInfoCarta(colorCarta = colorMazo) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMazoClick() }
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
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
                    IndicadorColorMazo(mazo.colores)
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CartaPreviewDeck(
                        imagenId = mazo.portadaId,
                        borderColor = colorBorde
                    )

                    Text(
                        text = "${mazo.cartasNormales}+${mazo.cartasHuevo}/${50}+${5}",
                        fontSize = 14.sp,
                        color = colorTexto
                    )

                    BotonMazo(
                        tipo = TipoBoton.EDITAR,
                        texto = "See Deck",
                        onClick = onMazoClick
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (mazo.tags.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            mazo.tags.take(2).forEach { tag ->
                                TagsDeck(tag)
                            }
                        }
                    }


                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                        IconButton(
                            onClick = onPerfilClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Ver perfil",
                                tint = colorTexto
                            )
                        }


                        IconButton(
                            onClick = onCopiarClick,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Copiar mazo",
                                tint = colorTexto
                            )
                        }
                    }
                }
            }
        }
    }
}