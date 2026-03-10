package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.ListaMazosUiState
import com.example.digitalhub.ui.theme.DigitalFont
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.Roboto

@Composable
fun ListaMazosContentido(
    uiState: ListaMazosUiState,
    mazoACopiar: String?,
    onBack: () -> Unit,
    onMetaClick: () -> Unit,
    onBusquedaChange: (String) -> Unit,
    onMazoClick: (String) -> Unit,
    onPerfilClick: (String) -> Unit,
    onCopiarMazo: (String) -> Unit,
    onConfirmarCopia: () -> Unit,
    onCancelarCopia: () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1565C0))
                    .padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    BotonX(onBack)
                }

                Text(
                    text = "DECK LIST",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DigitalFont,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                BotonesDentro(
                    text = "META",
                    fontSize = 24.sp,
                    fontFamily = DigitalFont,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .height(60.dp)
                        .width(200.dp),
                    isEnabled = true,
                    onClick = onMetaClick
                )
            }

            TextField(
                value = uiState.busqueda,
                onValueChange = onBusquedaChange,
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(2.dp, Color.White),
                singleLine = true
            )

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
                            text = uiState.errorMessage,
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                else -> {
                    val mazosFiltrados = uiState.mazos.filter { mazo ->
                        mazo.nombre.contains(uiState.busqueda, ignoreCase = true)
                    }

                    if (mazosFiltrados.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Decks not found",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentPadding = PaddingValues(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(mazosFiltrados) { mazo ->
                                CardMazoLista(
                                    mazo = mazo,
                                    onMazoClick = { onMazoClick(mazo.id) },
                                    onPerfilClick = { onPerfilClick(mazo.autorId) },
                                    onCopiarClick = { onCopiarMazo(mazo.id) }
                                )
                            }
                        }
                    }

                }
            }
        }
        if (mazoACopiar != null) {
            val mazo = uiState.mazos.find { it.id == mazoACopiar }
            if (mazo != null) {
                val cartasDelMazo = uiState.todasLasCartas?.filter { carta ->
                    mazo.cartas.any { it.cartaId == carta.id }
                } ?: emptyList()

                DialogoCopiarMazo(
                    mazo = mazo,
                    cartasDelMazo = cartasDelMazo,
                    onConfirmar = onConfirmarCopia,
                    onCancelar = onCancelarCopia
                )
            }
        }
    }
}