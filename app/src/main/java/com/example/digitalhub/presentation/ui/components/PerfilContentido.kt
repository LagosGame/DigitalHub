package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.digitalhub.R

@Composable
fun PerfilContentido(
    usuario: User,
    mazosPropios: List<Mazo>,
    todasLasCartas: List<Carta>,
    onBack: () -> Unit,
    onVerMazo: (String) -> Unit,
    esPropio: Boolean,
    onLogoutClick:()->Unit,
    onUpdateBiografia: (String) -> Unit,
    onUpdateCumpleanos: (String) -> Unit,
    onUpdateColorFavorito: (ColorCarta?) -> Unit,
    onUpdateAvatar: (Int) -> Unit,
    isSaving: Boolean = false,
    modifier: Modifier = Modifier
) {

    var mostrarDialogoBiografia by remember { mutableStateOf(false) }
    var mostrarDialogoCumpleanos by remember { mutableStateOf(false) }
    var mostrarDialogoColor by remember { mutableStateOf(false) }
    var mostrarDialogoAvatar by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        FondoPrincipal()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    BotonX(onBack)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(4.dp, Color.Black, CircleShape)
                        .clickable(enabled = esPropio) { mostrarDialogoAvatar = true },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(usuario.iconoId),
                        contentDescription = "Avatar",
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = usuario.username,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Kenyan,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Black, RoundedCornerShape(8.dp))
                                .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    BoxInfoCarta(
                        colorCarta = ColorCarta.BLACK,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Birthday:",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = usuario.cumpleanos.ifBlank { "Not specified" },
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            if (esPropio) {
                                IconoEditar(onClick = { mostrarDialogoCumpleanos = true })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    BoxInfoCarta(
                        colorCarta = usuario.colorFavorito ?: ColorCarta.RAINBOW,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val esColorClaro = usuario.colorFavorito in listOf(
                            ColorCarta.WHITE,
                            ColorCarta.YELLOW
                        )
                        val colorTexto = if (esColorClaro) Color.Black else Color.White
                        val colorIcono = if (esColorClaro) Color.Black else Color.White
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Favorite Color:",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorTexto
                                )
                                if (usuario.colorFavorito != null) {
                                    Box(
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        ColorPuntoConLetra(usuario.colorFavorito)
                                    }
                                } else {
                                    Text(
                                        text = "Not specified",
                                        fontSize = 14.sp,
                                        color = colorTexto
                                    )
                                }
                            }
                            if (esPropio) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = colorIcono,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { mostrarDialogoColor = true }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LetrasBordes(
                            text = "About you:",
                            fontSize = 18.sp,
                            fontFamily = Kenyan,
                            fontWeight = FontWeight.Bold,
                            textColor = Color.White,
                            strokeColor = Color.Black,
                            strokeWidth = 10f,
                            textAlign = TextAlign.Start
                        )
                        if (esPropio) {
                            IconoEditar(onClick = { mostrarDialogoBiografia = true })
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFB3E5FC), RoundedCornerShape(8.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = usuario.biografia.ifBlank { "EMPTY" },
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                LetrasBordes(
                    text = "Your decks:",
                    fontSize = 18.sp,
                    fontFamily = Kenyan,
                    fontWeight = FontWeight.Bold,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 10f,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(mazosPropios) { mazo ->
                val cartaEncontrada = todasLasCartas.find { it.id == mazo.portadaId }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                        CartaPreviewDeck(
                            portadaId = mazo.portadaId,
                            cartas = todasLasCartas,
                            borderColor = Color.Black
                        )
                    Text(
                        text = mazo.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )

                    BotonMazo(
                        tipo = TipoBoton.EDITAR,
                        texto = "SEE DECK",
                        onClick = { onVerMazo(mazo.id) }
                    )
                }
            }
            if (esPropio) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(60.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                            .background(Color.Gray, RoundedCornerShape(12.dp))
                            .clickable(enabled = !isSaving) { onLogoutClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "LOGOUT",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontFamily = Kenyan
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    if (mostrarDialogoBiografia) {
        DialogoEditarBiografia(
            currentBiografia = usuario.biografia,
            onDismiss = { mostrarDialogoBiografia = false },
            onConfirm = { newBiografia ->
                onUpdateBiografia(newBiografia)
                mostrarDialogoBiografia = false
            }
        )
    }

    if (mostrarDialogoCumpleanos) {
        DialogoEditarCumpleanos(
            currentCumpleanos = usuario.cumpleanos,
            onDismiss = { mostrarDialogoCumpleanos = false },
            onConfirm = { newCumpleanos ->
                onUpdateCumpleanos(newCumpleanos)
                mostrarDialogoCumpleanos = false
            }
        )
    }

    if (mostrarDialogoColor) {
        DialogoEditarColorFavorito(
            currentColor = usuario.colorFavorito,
            onDismiss = { mostrarDialogoColor = false },
            onConfirm = { newColor ->
                onUpdateColorFavorito(newColor)
                mostrarDialogoColor = false
            }
        )
    }

    if (isSaving) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
    if (mostrarDialogoAvatar) {
        DialogoSeleccionarAvatar(
            currentAvatarId = usuario.iconoId,
            onDismiss = { mostrarDialogoAvatar = false },
            onConfirm = { newAvatarId ->
                onUpdateAvatar(newAvatarId)
                mostrarDialogoAvatar = false
            }
        )
    }
}

