package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.estiloParaColor

@Composable
fun BoxInfoCarta(
    modifier: Modifier = Modifier,
    colorCarta: ColorCarta,
    content: @Composable () -> Unit
) {
    val estilo = estiloParaColor(colorCarta)
    val bordeFinal = when (colorCarta) {
        ColorCarta.BLACK -> Color.White
        ColorCarta.WHITE -> Color.Black
        ColorCarta.YELLOW -> Color.Black
        else -> estilo.borde
    }

    val textoFinal = when (colorCarta) {
        ColorCarta.WHITE -> Color.Black
        ColorCarta.YELLOW -> Color.Black
        else -> estilo.texto
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(estilo.fondo)
            .border(
                width = 3.dp,
                color = bordeFinal,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Column {
            CompositionLocalProvider(
                LocalContentColor provides textoFinal
            ) {
                Column {
                    ProvideTextStyle(
                        value = TextStyle(fontFamily = Kenyan)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}