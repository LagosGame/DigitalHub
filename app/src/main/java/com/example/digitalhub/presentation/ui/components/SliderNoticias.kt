package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.digitalhub.data.model.Noticia

@Composable
fun SliderNoticias(
    noticias: List<Noticia>,
    indice: Int,
    onCambiar: (Int) -> Unit,
    onClickImagen: (String) -> Unit
){
    Box(
        modifier = Modifier.height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        //Foto
        Image(
            painter = painterResource(noticias[indice].imagen),
            contentDescription = "Noticia ${indice + 1}",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable {
                    onClickImagen(noticias[indice].url)
                },
            contentScale = ContentScale.Crop
        )

        //Botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Botón Anterior
            IconButton(
                onClick = {
                    onCambiar(if (indice > 0) indice - 1 else noticias.lastIndex)
                },
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Anterior",
                    tint = Color.White
                )
            }

            // Botón Siguiente
            IconButton(
                onClick = {
                    onCambiar(if (indice < noticias.lastIndex) indice + 1 else 0)
                },
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Siguiente",
                    tint = Color.White
                )
            }
        }
    }
}