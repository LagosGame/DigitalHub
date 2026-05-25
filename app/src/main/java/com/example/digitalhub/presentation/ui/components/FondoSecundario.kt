package com.example.digitalhub.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.example.digitalhub.R

@Composable
fun FondoSecundario(){
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Image(
        painter = painterResource(R.drawable.cardback),
        contentDescription = null,
        modifier = Modifier.fillMaxSize().scale(1.7f),
        contentScale = if (isLandscape) ContentScale.FillBounds else ContentScale.FillHeight
    )
}