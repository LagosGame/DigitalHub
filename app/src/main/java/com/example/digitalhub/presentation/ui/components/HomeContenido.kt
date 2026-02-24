package com.example.digitalhub.presentation.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.data.local.noticias
import com.example.digitalhub.presentation.ui.state.MainUiState
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.Roboto
@Composable
fun HomeContentido(
    uiState: MainUiState,
    onCambiarIndice: (Int) -> Unit,
    onBiblioteca: () -> Unit,
    onConstruir: () -> Unit,
    onLista: () -> Unit,
    onPerfil: () -> Unit
) {
    val context = LocalContext.current

    FondoPrincipal()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        //Slider
        Box(
            modifier = Modifier
                .height(300.dp)
                .width(350.dp)
                .background(Color.Blue.copy(alpha = 0.4f))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            SliderNoticias(
                noticias = noticias,
                indice = uiState.indiceSlider,
                onCambiar = onCambiarIndice,
                onClickImagen = { url ->
                    abrirUrl(context, url)
                }
            )

            IndicadorSlider(
                total = noticias.size,
                indice = uiState.indiceSlider,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        //Botones navegación
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = 400.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BotonesDentro(
                text = "Library",
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp),
                fontSize = 32.sp,
                fontFamily = Kenyan,
                fontWeight = FontWeight.Normal,
                isEnabled = true,
                onClick = onBiblioteca
            )

            BotonesDentro(
                text = "Build deck",
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp),
                fontSize = 32.sp,
                fontFamily = Kenyan,
                fontWeight = FontWeight.Normal,
                isEnabled = true,
                onClick = onConstruir
            )

            BotonesDentro(
                text = "Deck list",
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp),
                fontSize = 32.sp,
                fontFamily = Kenyan,
                fontWeight = FontWeight.Normal,
                isEnabled = true,
                onClick = onLista
            )

            BotonesDentro(
                text = "Profile",
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp),
                fontSize = 32.sp,
                fontFamily = Kenyan,
                fontWeight = FontWeight.Normal,
                isEnabled = true,
                onClick = onPerfil
            )
        }
    }

    //Botón de opciones
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            onClick = { /* TODO: Opciones */ },
            modifier = Modifier.background(
                color = Color.White,
                shape = CircleShape
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Build,
                contentDescription = "Settings",
                tint = Color.Black
            )
        }
    }
}

//Función abrir URLs
private fun abrirUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Preview
@Composable
fun HomeContentidoPreview() {
    HomeContentido(
        uiState = MainUiState(),
        onCambiarIndice = {},
        onBiblioteca = {},
        onConstruir = {},
        onLista = {},
        onPerfil = {}
    )
}