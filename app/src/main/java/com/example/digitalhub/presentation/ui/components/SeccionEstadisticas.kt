package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.EstadisticasEdit
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun SeccionEstadisticas(
    estadisticas: EstadisticasEdit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LetrasBordes(
                text = "Stats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Kenyan,
                textColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 20f,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, "Editar", tint = Color.White)
            }
        }

        //Estadiscticas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                .background(Color.Black, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EstadisticaItem("Attack", estadisticas.ataque)
                EstadisticaItem("Versatility", estadisticas.versatilidad)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EstadisticaItem("Defense", estadisticas.defensa)
                EstadisticaItem("Comeback", estadisticas.recuperacion)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                EstadisticaItem("Consistency", estadisticas.consistencia)
            }
        }
    }
}

@Composable
private fun EstadisticaItem(nombre: String, valor: Int) {
    Text(
        text = "$nombre → $valor",
        fontSize = 16.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}