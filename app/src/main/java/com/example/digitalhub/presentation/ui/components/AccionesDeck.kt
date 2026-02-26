package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccionesDeck(
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier.width(100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        BotonMazo(
            tipo = TipoBoton.EDITAR,
            onClick = onEdit,
            texto = "Edit"
        )
        BotonMazo(
            tipo = TipoBoton.ELIMINAR,
            onClick = onDelete,
            texto = "Delete"
        )
    }
}