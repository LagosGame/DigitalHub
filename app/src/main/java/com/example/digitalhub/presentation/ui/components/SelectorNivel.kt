package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Nivel

@Composable
fun SelectorNivel(
    onSelect: (Nivel) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Nivel.values().forEach { nivel ->
            BotonFiltro(
                texto = nivel.etiqueta,
                onClick = { onSelect(nivel) }
            )
        }
    }
}