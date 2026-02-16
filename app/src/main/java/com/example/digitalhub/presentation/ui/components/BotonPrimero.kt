package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val ColorBoton = Color(0xFF004572)
@Composable

fun BotonEntrar(
    text : String,
    modifier: Modifier= Modifier,
    enabled : Boolean = true,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight= FontWeight.Normal,
    onClick : () -> Unit
){
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Gray,
            containerColor = ColorBoton

        )
    ){
        Text(text=text,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }

}