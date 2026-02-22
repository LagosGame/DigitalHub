package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.expansiones

@Composable
fun SelectorExpansion(
    onSelect: (Expansion) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        expansiones.forEach { expansion ->
            BotonFiltro(
                texto = expansion.codigo,
                onClick = { onSelect(expansion) }
            )
        }
    }
}