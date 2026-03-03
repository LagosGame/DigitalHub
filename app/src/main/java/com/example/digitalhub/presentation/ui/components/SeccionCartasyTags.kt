package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun SeccionCartasyTags(
    cartasImportantes: List<Carta>,
    tags: List<String>,
    onAñadirCarta: () -> Unit,
    onEliminarCarta: (String) -> Unit,
    onAñadirTag: () -> Unit,
    onEliminarTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //cartas
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LetrasBordes(
                    text = "Key Cards",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 20f,
                    textAlign = TextAlign.Center
                )
                Row {
                    IconButton(onClick = onAñadirCarta) {
                        Icon(Icons.Default.Add, "Añadir", tint = Color.White)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartasImportantes) { carta ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CartaPreviewDeck(
                            imagenId = carta.imagenId,
                            borderColor = Color.White
                        )
                        Text(
                            text = carta.nombre,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )
                        IconButton(onClick = { onEliminarCarta(carta.id) }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }

        //etiquetas
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LetrasBordes(
                    text = "Tags",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 20f,
                    textAlign = TextAlign.Center
                )
                Row {
                    IconButton(onClick = onAñadirTag) {
                        Icon(Icons.Default.Add, "Añadir", tint = Color.White)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),  // Toma el espacio que queda
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tags) { tag ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TagsDeck(tag)
                        IconButton(onClick = { onEliminarTag(tag) }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}