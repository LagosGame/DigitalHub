package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.ui.theme.Kenyan
import com.example.digitalhub.ui.theme.Roboto
import com.example.digitalhub.ui.theme.estiloParaColor

enum class TipoBoton {
    CANCELAR,
    EDITAR,
    ELIMINAR,
    PERSONALIZADO,
    COLOR_CARTA
}

@Composable
fun BotonMazo(
    tipo: TipoBoton = TipoBoton.PERSONALIZADO,
    texto: String,
    enabled: Boolean = true,
    colorPersonalizado: Color? = null,
    colorCarta: ColorCarta? = null,
    onClick: () -> Unit
) {
    val colorFondo: Color
    val colorTexto: Color

    when (tipo) {
        TipoBoton.CANCELAR -> {
            colorFondo = Color.Red
            colorTexto = Color.White
        }
        TipoBoton.EDITAR -> {
            colorFondo = Color(0xFF004572)
            colorTexto = Color.White
        }
        TipoBoton.ELIMINAR -> {
            colorFondo = Color.White
            colorTexto = Color.Black
        }
        TipoBoton.COLOR_CARTA -> {
            val estilo = colorCarta?.let { estiloParaColor(it) }
            colorFondo = estilo?.fondo ?: Color.Gray
            colorTexto = estilo?.texto ?: Color.White
        }
        TipoBoton.PERSONALIZADO -> {
            colorFondo = colorPersonalizado ?: Color.Gray
            colorTexto = Color.White
        }
    }

    val finalColorFondo = if (enabled) colorFondo else Color.Gray
    val finalColorTexto = if (enabled) colorTexto else Color.DarkGray
    val finalColorBorde = if (tipo == TipoBoton.COLOR_CARTA && colorCarta != null) {
        estiloParaColor(colorCarta).borde
    } else {
        Color.Black
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 3.dp,
                color = finalColorBorde,
                shape = RoundedCornerShape(12.dp)
            )
            .background(finalColorFondo)
            .width(130.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(enabled = enabled) { onClick() }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                ProvideTextStyle(
                    value = TextStyle(fontFamily = Kenyan)
                ) {
                    Text(
                        text = texto,
                        color = finalColorTexto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}