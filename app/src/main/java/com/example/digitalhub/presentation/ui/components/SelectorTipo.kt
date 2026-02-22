package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.TipoCarta

@Composable
fun SelectorTipo(
    onSelect: (TipoCarta) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TipoCarta.values().forEach { tipo ->
            BotonFiltro(
                texto = tipo.nombreDisplay,
                onClick = { onSelect(tipo) }
            )
        }
    }
}