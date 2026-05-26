package com.example.digitalhub.presentation.ui.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.ComentariosViewModelFactory
import com.example.digitalhub.presentation.ui.components.BotonX
import com.example.digitalhub.presentation.ui.components.ComentarioItem
import com.example.digitalhub.presentation.ui.components.DialogoCOmentar
import com.example.digitalhub.presentation.ui.components.FondoPrincipal
import com.example.digitalhub.presentation.ui.components.IndicadorColorMazo
import com.example.digitalhub.presentation.viewmodel.ComentariosViewModel

@Composable
fun ComentariosScreen(
    mazoId: String,
    viewModel: ComentariosViewModel = viewModel(factory = ComentariosViewModelFactory(mazoId)),
    onBack: () -> Unit,
    onPerfilClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage ?: "Error",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            uiState.mazo != null -> {
                val mazo = uiState.mazo!!

                Column(modifier = Modifier.fillMaxSize()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.9f))
                            .padding(16.dp, top = 50.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = mazo.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )

                        IndicadorColorMazo(mazo.colores)

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Black, CircleShape)
                                .clickable { onPerfilClick(mazo.userId) }
                        ) {
                            val usuarioMazo = uiState.usuarios[mazo.userId]

                            if (usuarioMazo?.iconoId != null && usuarioMazo.iconoId != 0) {
                                Image(
                                    painter = painterResource(id = usuarioMazo.iconoId),
                                    contentDescription = "Avatar del autor",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFFFFEB3B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Default Avatar",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }


                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.comentarios) { comentario ->
                                ComentarioItem(
                                    comentario = comentario,
                                    usuarioActualId = uiState.currentUserId,
                                    onLikeClick = { viewModel.toggleLike(it) },
                                    onResponderClick = { viewModel.responderComentario(it) },
                                    onPerfilClick = onPerfilClick
                                )
                            }
                        }


                        FloatingActionButton(
                            onClick = { viewModel.abrirDialogoComentar() },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            containerColor = Color(0xFF1565C0)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Comment",
                                tint = Color.White
                            )
                        }
                    }
                }

                if (uiState.mostrarDialogoComentar) {
                    DialogoCOmentar(
                        titulo = "New Comment",
                        textoComentario = uiState.nuevoComentario,
                        onTextoChange = viewModel::actualizarTextoComentario,
                        onPublicar = viewModel::enviarComentario,
                        onCancelar = viewModel::cerrarDialogos
                    )
                }

                if (uiState.mostrarDialogoResponder) {
                    DialogoCOmentar(
                        titulo = "Respond",
                        textoComentario = uiState.nuevoComentario,
                        onTextoChange = viewModel::actualizarTextoComentario,
                        onPublicar = viewModel::enviarComentario,
                        onCancelar = viewModel::cerrarDialogos
                    )
                }
            }
        }
    }
}