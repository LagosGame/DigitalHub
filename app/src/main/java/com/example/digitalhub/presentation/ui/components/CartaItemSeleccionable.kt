package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Carta

@Composable
fun CartaItemSeleccionable(
    carta: Carta,
    seleccionada: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(0.7f)
            .border(
                width = if (seleccionada) 3.dp else 0.dp,
                color = if (seleccionada) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onToggle() }
    ) {
        Image(
            painter = painterResource(carta.imagenId),
            contentDescription = carta.nombre,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit
        )

        if (seleccionada) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Seleccionado",
                tint = Color.Green,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.White, RoundedCornerShape(50))
            )
        }
    }
}