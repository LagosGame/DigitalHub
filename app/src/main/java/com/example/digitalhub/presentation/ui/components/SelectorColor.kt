package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun SelectorColor(
    onSelect: (ColorCarta)-> Unit
){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
    {
        ColorCarta.values().forEach { colorCarta ->
            BotonColor(
                text = colorCarta.nombreDisplay.first().toString(),
                color = colorCarta.toColor(),
                onClick = {onSelect(colorCarta)}
            )
        }
    }
}

fun ColorCarta.toColor(): Color{
    return when(this){
        ColorCarta.RED -> Color.Red
        ColorCarta.BLUE -> Color.Blue
        ColorCarta.YELLOW -> Color.Yellow
        ColorCarta.GREEN -> Color.Green
        ColorCarta.BLACK -> Color.Black
        ColorCarta.PURPLE -> Color(0xFF9C27B0)
        ColorCarta.WHITE -> Color.White
        ColorCarta.RAINBOW -> Color.Gray
    }
}