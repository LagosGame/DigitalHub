package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Carta

@Composable
fun GridBiblioteca(
    cartas: List<Carta>,
    columnas: Int = 4,
    modifier: Modifier = Modifier,
    bottomPadding: Int = 180,
    onCartaClick:(Carta)-> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnas),
        modifier = modifier.padding(8.dp),
        contentPadding = PaddingValues(bottom = bottomPadding.dp)
    ) {
        items(cartas) { carta ->
            CartaItem(
                carta = carta,
                onClick = { onCartaClick(carta) }
            )
        }
    }
}