package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Carta

@Composable
fun CartaItem(
    carta: Carta,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.7f)
            .background(Color.LightGray)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(carta.imagenId),
            contentDescription = carta.nombre,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}