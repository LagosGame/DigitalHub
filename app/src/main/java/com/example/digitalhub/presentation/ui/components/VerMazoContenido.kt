package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.EstadisticasEdit
import com.example.digitalhub.presentation.ui.state.VerMazoUiState

@Composable
fun VerMazoContentido(
    uiState: VerMazoUiState,
    mostrarDialogoCopiar: Boolean,
    onBack: () -> Unit,
    onPerfilAutor: (String) -> Unit,
    onAbrirDialogoCopiar: () -> Unit,
    onConfirmarCopia: () -> Unit,
    onCancelarCopia: () -> Unit,
    onComentariosClick: () -> Unit
) {
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
                        text = uiState.errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            uiState.mazo != null -> {
                val mazo = uiState.mazo
                val cartasDelMazo = uiState.todasLasCartas.filter { carta ->
                    mazo.cartas.any { it.cartaId == carta.id }
                }
                val cartasImportantes = uiState.todasLasCartas.filter { carta ->
                    carta.id in mazo.cartasImportantes
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    ZonaMazoVista(
                        mazo = mazo,
                        cartasDelMazo = cartasDelMazo,
                        onBack = onBack,
                        usuario = uiState.usuario,
                        onAbrirDialogoCopiar = onAbrirDialogoCopiar,
                        onPerfilAutor = { onPerfilAutor(mazo.userId) }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color.Black)
                    )
                    SeccionDetallesVista(
                        descripcion = mazo.descripcion,
                        estrategias = mazo.estrategias,
                        cartasImportantes = cartasImportantes,
                        estadisticas = EstadisticasEdit(
                            ataque = mazo.estadisticas.ataque,
                            defensa = mazo.estadisticas.defensa,
                            consistencia = mazo.estadisticas.consistencia,
                            versatilidad = mazo.estadisticas.versatilidad,
                            recuperacion = mazo.estadisticas.recuperacion
                        ),
                        todasLasCartas = uiState.todasLasCartas,
                        onComentariosClick = onComentariosClick
                    )
                }

                if (mostrarDialogoCopiar) {
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
}