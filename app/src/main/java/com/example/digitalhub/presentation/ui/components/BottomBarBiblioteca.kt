package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.BibliotecaUiState

@Composable
fun BottomBarBiblioteca(
    uiState: BibliotecaUiState,
    onBusquedaChange: (String) -> Unit,
    onActivarFavoritas: () -> Unit,
    onActivarAlternativas: () -> Unit,
    onActivarSoloMiBiblioteca: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.busqueda,
            onValueChange = onBusquedaChange,
            placeholder = { Text("Search...") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Collection", color = Color.White, fontSize = 10.sp)
                Switch(
                    checked = uiState.soloMiBiblioteca,
                    onCheckedChange = { onActivarSoloMiBiblioteca() }
                )
            }
            Spacer(modifier = Modifier.width(4.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Favourites", color = Color.White, fontSize = 10.sp)
                Switch(
                    checked = uiState.soloFav,
                    onCheckedChange = { onActivarFavoritas() }
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Alt. Art", color = Color.White, fontSize = 10.sp)
                Switch(
                    checked = uiState.soloAlt,
                    onCheckedChange = { onActivarAlternativas() }
                )
            }
        }
    }
}