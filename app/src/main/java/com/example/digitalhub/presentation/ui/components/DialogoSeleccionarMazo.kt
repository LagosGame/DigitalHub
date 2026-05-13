package com.example.digitalhub.presentation.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun DialogoSeleccionarMazo(
    mazosDisponibles: List<Mazo>,
    cartas: List<Carta>,
    onSeleccionarMazo: (String) -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        title = {
            Text(
                text = "SELECT A DECK",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Kenyan,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (mazosDisponibles.isEmpty()) {
                    Text(
                        text = "YOU DON'T HAVE ANY DECKS. CREATE ONE",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(mazosDisponibles) { mazo ->
                            MazoItem(
                                mazo = mazo,
                                cartas = cartas,
                                onClick = { onSeleccionarMazo(mazo.id) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            BotonMazo(
                tipo = TipoBoton.CANCELAR,
                texto = "Cancel",
                onClick = onCancelar
            )
        },
        dismissButton = {},
        containerColor = Color(0xFF1565C0),
        shape = RoundedCornerShape(16.dp)
    )
}