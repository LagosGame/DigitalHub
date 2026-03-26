package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun TierHeader(tierNumber: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.Black)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        LetrasBordes(
            text = "TIER $tierNumber",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Kenyan,
            textColor = Color.White,
            strokeColor = Color.Black,
            strokeWidth = 6f,
            textAlign = TextAlign.Start
        )
    }
}