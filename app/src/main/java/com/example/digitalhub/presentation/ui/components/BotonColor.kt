package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.Roboto

@Composable
fun BotonColor(
    text: String,
    color: Color,
    onClick:()-> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() }
            ,
        contentAlignment = Alignment.Center
    ) {
        LetrasBordes(
            text = text,
            fontSize = 14.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            textColor = Color.White,
            strokeColor = Color.Black,
            strokeWidth = 5f,
            textAlign = TextAlign.Center
        )
    }
}