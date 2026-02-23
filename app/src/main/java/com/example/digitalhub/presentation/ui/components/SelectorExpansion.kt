package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.expansiones

@Composable
fun SelectorExpansion(
    onSelect: (Expansion?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            BotonFiltro(
                texto = "ALL",
                onClick = { onSelect(null) }
            )
        }
        items(expansiones) { expansion ->
            BotonFiltro(
                texto = expansion.codigo,
                onClick = { onSelect(expansion) }
            )
        }
    }
}