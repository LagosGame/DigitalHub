package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun SelectorColor(
    onSelect: (ColorCarta?)-> Unit
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            BotonColor(
                text = "ALL",
                color = Color.DarkGray,
                onClick = { onSelect(null) }
            )
        }
        items(ColorCarta.values().toList()) { colorCarta ->
            BotonColor(
                text = colorCarta.nombreDisplay.first().toString(),
                color = colorCarta.toColor(),
                onClick = { onSelect(colorCarta) }
            )
        }
    }
}

fun ColorCarta.toColor(): Color {
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