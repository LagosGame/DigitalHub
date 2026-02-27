package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Carta

@Composable
fun ZonaBiblioteca(
    cartas: List<Carta>,
    isLoading: Boolean,
    onCartaClick: (Carta) -> Unit,
    modifier: Modifier = Modifier
) {
    var busqueda by remember { mutableStateOf("") }

    val cartasFiltradas = remember(cartas, busqueda) {
        if (busqueda.isBlank()) {
            cartas
        } else {
            cartas.filter {
                it.nombre.contains(busqueda, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (cartas.isEmpty()) {
            Text(
                text = "You don't have cards in your library",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    placeholder = { Text("Search card...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true
                )
                Text(
                    text = "${cartasFiltradas.size} cards",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                GridBiblioteca(
                    cartas = cartasFiltradas,
                    modifier = Modifier.padding(8.dp),
                    onCartaClick = { carta ->
                        onCartaClick(carta)
                    }
                )
            }
        }
    }
}