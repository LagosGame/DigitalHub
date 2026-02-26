package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun ColorPunto(colorCarta: ColorCarta) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(
                color = colorCarta.toColor(),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}