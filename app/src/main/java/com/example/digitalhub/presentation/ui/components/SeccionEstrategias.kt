package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.digitalhub.domain.model.Estrategia
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun SeccionEstrategias(
    estrategias: List<Estrategia>,
    todasLasCartas: List<Carta>,
    onAñadir: () -> Unit,
    onEliminar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LetrasBordes(
                text = "Strategies",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Kenyan,
                textColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 20f,
                textAlign = TextAlign.Center
            )

            IconButton(onClick = onAñadir) {
                Icon(Icons.Default.Add, "Añadir", tint = Color.White)
            }
        }

        if (estrategias.isEmpty()) {
            LetrasBordes(
                text = "Without strategies",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Kenyan,
                textColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 20f,
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(estrategias) { index, estrategia ->
                    EstrategiaItem(
                        estrategia = estrategia,
                        cartas = todasLasCartas.filter { it.id in estrategia.cartasIds },
                        onEliminar = { onEliminar(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EstrategiaItem(
    estrategia: Estrategia,
    cartas: List<Carta>,
    onEliminar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
            .background(Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = estrategia.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(cartas) { carta ->
                CartaPreviewDeck(
                    imagenId = carta.imagenId,
                    borderColor = Color.White
                )
            }
        }
    }
}