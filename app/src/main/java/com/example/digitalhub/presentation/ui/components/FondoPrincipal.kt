package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.digitalhub.R

@Composable
fun FondoPrincipal(){
    Image(painter = painterResource(R.drawable.fondoprinc),
        contentDescription = null,
        modifier = Modifier.fillMaxSize().scale(1.3f),
        contentScale = ContentScale.FillHeight)
}