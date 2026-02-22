package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SelectorCostes(
    onSelect: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        (0..20).forEach { coste ->
            BotonFiltro(
                texto = coste.toString(),
                onClick = { onSelect(coste) }
            )
        }
    }
}