package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.digitalhub.domain.model.Mazo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.R
@Composable
fun MazoItem(
    mazo: Mazo,
    cartas: List<Carta>,
    onClick: () -> Unit
) {
    val cartaPortada = cartas.find { it.id == mazo.portadaId }
    val imagenId = cartaPortada?.imagenId ?: R.drawable.bt1001
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(imagenId),
            contentDescription = mazo.nombre,
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = mazo.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${mazo.cartasNormales}+${mazo.cartasHuevo}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                IndicadorColorMazo(mazo.colores)
            }
        }

        Text(
            text = ">",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )
    }
}