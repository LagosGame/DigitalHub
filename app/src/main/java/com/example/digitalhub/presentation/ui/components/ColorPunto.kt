package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun ColorPunto(colorCarta: ColorCarta) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                color = colorCarta.toColor(),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}
@Composable
fun ColorPuntoConLetra(color: ColorCarta) {
    Box(
        modifier = Modifier.size(42.dp),
        contentAlignment = Alignment.Center
    ) {
        ColorPunto(colorCarta = color)

        LetrasBordes(
            text = obtenerLetraColor(color),
            fontSize = 14.sp,
            fontFamily = Kenyan,
            fontWeight = FontWeight.Bold,
            textColor = Color.White,
            strokeColor = Color.Black,
            strokeWidth = 3f,
            textAlign = TextAlign.Center
        )
    }
}
private fun obtenerLetraColor(color: ColorCarta): String {
    return when (color) {
        ColorCarta.RED -> "R"
        ColorCarta.BLUE -> "B"
        ColorCarta.YELLOW -> "Y"
        ColorCarta.GREEN -> "G"
        ColorCarta.BLACK -> "K"
        ColorCarta.PURPLE -> "P"
        ColorCarta.WHITE -> "W"
        ColorCarta.RAINBOW -> "M"
    }
}