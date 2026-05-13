package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun DialogoReportarError(
    mensajeError: String,
    onMensajeChange: (String) -> Unit,
    onEnviar: () -> Unit,
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
                text = "REPORT ERROR",
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
                Text(
                    text = "Describe the error:",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TextField(
                    value = mensajeError,
                    onValueChange = onMensajeChange,
                    placeholder = { Text("Example: It doesnt save my deck...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    maxLines = 6
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                BotonMazo(
                    tipo = TipoBoton.EDITAR,
                    texto = "Send",
                    onClick = onEnviar
                )

                BotonMazo(
                    tipo = TipoBoton.CANCELAR,
                    texto = "Cancel",
                    onClick = onCancelar

                )
            }
        },
        dismissButton = {},
        containerColor = Color(0xFFE53935),
        shape = RoundedCornerShape(16.dp)
    )
}