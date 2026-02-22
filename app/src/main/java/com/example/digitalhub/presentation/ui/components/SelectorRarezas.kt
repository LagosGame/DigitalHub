package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.RarezaCarta

@Composable
fun SelectorRarezas(
    onSelect: (RarezaCarta) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RarezaCarta.values().forEach { rareza ->
            BotonFiltro(
                texto = rareza.letra,
                onClick = { onSelect(rareza) }
            )
        }
    }
}