package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.digitalhub.R

@Composable
fun CartaPreviewDeck(
    imagenId: Int = R.drawable.bt1025
) {
    Box(
        modifier = Modifier
            .size(width = 50.dp, height = 70.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(imagenId),
            contentDescription = "Portada del mazo",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}