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
    modifier: Modifier = Modifier,
    onCartaClick:(Carta)-> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.padding(8.dp),
        contentPadding = PaddingValues(bottom = 180.dp)
    ) {
        items(cartas) { carta ->
            CartaItem(
                carta = carta,
                onClick = { onCartaClick(carta) }
            )
        }
    }
}