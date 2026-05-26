package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun ZonaMazoVista(
    mazo: Mazo,
    cartasDelMazo: List<Carta>,
    onBack: () -> Unit,
    onAbrirDialogoCopiar: () -> Unit,
    usuario: User?,
    onPerfilAutor: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .clipToBounds()
    ) {
        Box(modifier = Modifier.scale(1.9f).padding(top = 40.dp)) {
            FondoSecundario()
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (mazo.esFavorito) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(32.dp)
                            .border(2.dp, Color.Black, CircleShape)
                            .background(Color.White, CircleShape)
                            .padding(4.dp)
                    )
                }

                LetrasBordes(
                    text = mazo.nombre,
                    fontSize = 24.sp,
                    fontFamily = Kenyan,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 10f,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                )


                LetrasBordes(
                    text = "${mazo.cartasNormales}+${mazo.cartasHuevo}/${50}+${5}",
                    fontSize = 16.sp,
                    fontFamily = Kenyan,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 5f,
                    textAlign = TextAlign.Center
                )

                IndicadorColorMazo(mazo.colores)

            }

            Spacer(modifier = Modifier.height(8.dp))

            if (cartasDelMazo.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add cards from the library",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cartasDelMazo) { carta ->
                        val cantidad = mazo.cartas.find { it.cartaId == carta.id }?.cantidad ?: 0
                        CartaItemMazo(
                            carta = carta,
                            cantidad = cantidad,
                            onClick = { }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (usuario != null) {
                    Image(
                        painter = painterResource(usuario.iconoId),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Black, CircleShape)
                            .background(Color.White, CircleShape)
                            .clickable { onPerfilAutor() }
                    )
                } else {
                    IconButton(
                        onClick = onPerfilAutor,
                        modifier = Modifier
                            .size(60.dp)
                            .border(2.dp, Color.Black, CircleShape)
                            .background(Color(0xFFFFEB3B), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                BotonMazo(
                    tipo = TipoBoton.EDITAR,
                    onClick = onAbrirDialogoCopiar,
                    texto = "Copy"
                )
            }
        }
    }
}