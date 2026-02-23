package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectorCostes(
    onSelect: (Int?) -> Unit
) {
    val costes = (0..20).toList()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            BotonColor(
                text = "ALL",
                color = Color.DarkGray,
                onClick = { onSelect(null) }
            )
        }
        items(costes) { coste ->
            BotonFiltro(
                texto = coste.toString(),
                onClick = { onSelect(coste) }
            )
        }
    }
}