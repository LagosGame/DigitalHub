package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun LetrasBordes(
    text: String,
    fontSize: TextUnit,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    textColor: Color,
    strokeColor: Color,
    strokeWidth: Float,
    textAlign: TextAlign,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        textAlign = textAlign,
        color = textColor,
        modifier = modifier,
        style = TextStyle(
            shadow = Shadow(
                color = strokeColor,
                offset = Offset(0f, 0f),
                blurRadius = strokeWidth / 2
            )
        )
    )
}