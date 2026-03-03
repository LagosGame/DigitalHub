package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.EstadisticasEdit

@Composable
fun DialogoEditarEstadisticas(
    estadisticasActuales: EstadisticasEdit,
    onConfirm: (EstadisticasEdit) -> Unit,
    onDismiss: () -> Unit
) {
    var ataque by remember { mutableIntStateOf(estadisticasActuales.ataque) }
    var defensa by remember { mutableIntStateOf(estadisticasActuales.defensa) }
    var consistencia by remember { mutableIntStateOf(estadisticasActuales.consistencia) }
    var versatilidad by remember { mutableIntStateOf(estadisticasActuales.versatilidad) }
    var recuperacion by remember { mutableIntStateOf(estadisticasActuales.recuperacion) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit stats",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SliderEstadistica("Attack", ataque) { ataque = it }
                SliderEstadistica("Defense", defensa) { defensa = it }
                SliderEstadistica("Consistency", consistencia) { consistencia = it }
                SliderEstadistica("Versatility", versatilidad) { versatilidad = it }
                SliderEstadistica("Comeback", recuperacion) { recuperacion = it }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    EstadisticasEdit(
                        ataque = ataque,
                        defensa = defensa,
                        consistencia = consistencia,
                        versatilidad = versatilidad,
                        recuperacion = recuperacion
                    )
                )
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun SliderEstadistica(
    nombre: String,
    valor: Int,
    onValueChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(nombre, fontSize = 14.sp)
            Text(valor.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = valor.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 1f..10f,
            steps = 8,
            modifier = Modifier.fillMaxWidth()
        )
    }
}