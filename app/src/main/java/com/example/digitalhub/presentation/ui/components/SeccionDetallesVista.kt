package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.Estrategia
import com.example.digitalhub.presentation.ui.state.EstadisticasEdit

@Composable
fun SeccionDetallesVista(
    descripcion: String,
    estrategias: List<Estrategia>,
    cartasImportantes: List<Carta>,
    estadisticas: EstadisticasEdit,
    todasLasCartas: List<Carta>,
    onComentariosClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        SeccionDescripcion(
            titulo = "Deck description",
            contenido = descripcion,
            onEdit = {},
            readOnly = true
        )

        SeccionEstrategias(
            estrategias = estrategias,
            todasLasCartas = todasLasCartas,
            onAñadir = {},
            onEliminar = {},
            readOnly = true
        )

        SeccionCartasVista(
            cartasImportantes = cartasImportantes
        )


        SeccionEstadisticas(
            estadisticas = estadisticas,
            onEdit = {},
            readOnly = true
        )

        BotonMazo(
            tipo = TipoBoton.EDITAR,
            texto = "Comments",
            onClick = onComentariosClick
        )
    }
}