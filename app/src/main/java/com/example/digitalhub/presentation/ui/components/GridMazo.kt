package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Carta

@Composable
fun GridMazo(
    cartas: List<Carta>,
    obtenerCantidad: (String) -> Int,
    modifier: Modifier = Modifier,
    onCartaClick: (Carta) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        items(cartas) { carta ->
            val cantidad = obtenerCantidad(carta.id)

            CartaItemMazo(
                carta = carta,
                cantidad = cantidad,
                onClick = { onCartaClick(carta) }
            )
        }
    }
}