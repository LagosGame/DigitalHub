package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Arquetipo
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.ui.theme.Kenyan


@Composable
fun ArquetipoCard(
    arquetipo: Arquetipo,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(obtenerColorArquetipo(arquetipo.color))
            .border(2.dp, Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LetrasBordes(
            text = arquetipo.nombre,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Kenyan,
            textColor = Color.White,
            strokeColor = Color.Black,
            strokeWidth = 6f,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(arquetipo.imagenId),
            contentDescription = arquetipo.nombre,
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
private fun obtenerColorArquetipo(colorCarta: ColorCarta): Color {
    return when (colorCarta) {
        ColorCarta.RED -> Color(0xFFE53935)
        ColorCarta.BLUE -> Color(0xFF1E88E5)
        ColorCarta.YELLOW -> Color(0xFFFDD835)
        ColorCarta.GREEN -> Color(0xFF43A047)
        ColorCarta.BLACK -> Color(0xFF424242)
        ColorCarta.PURPLE -> Color(0xFF8E24AA)
        ColorCarta.WHITE -> Color(0xFFEEEEEE)
        ColorCarta.RAINBOW -> Color(0x74ABABAB)
    }
}
