package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.ColorCarta

@Composable
fun IndicadorColorMazo(colores: List<ColorCarta>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        when {
            colores.isEmpty() -> {
                ColorPuntoConLetra(ColorCarta.RAINBOW)
            }
            colores.size == 1 -> {
                ColorPuntoConLetra(colores.first())
            }
            colores.size == 2 -> {
                colores.forEach { ColorPuntoConLetra(it)}
            }
            else -> {
                Arcoiris()
            }
        }
    }
}