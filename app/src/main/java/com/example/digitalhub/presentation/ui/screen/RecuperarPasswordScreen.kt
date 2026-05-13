package com.example.digitalhub.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.components.*
import com.example.digitalhub.ui.theme.DigitalFont
import com.example.digitalhub.ui.theme.Roboto
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecuperarPasswordScreen(
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var esExito by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            LetrasBordes(
                text = "RECOVER\nPASSWORD",
                fontSize = 48.sp,
                fontFamily = DigitalFont,
                fontWeight = FontWeight.Normal,
                textColor = Color.White,
                strokeColor = Color.Black,
                strokeWidth = 15f,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            LetrasBordes(
                text = "Enter your email to reset password",
                fontSize = 16.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                textColor = Color.White,
                strokeColor = Color.Blue,
                strokeWidth = 4f,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextfieldUsuario(
                value = email,
                placeholder = "Email",
                onValueChange = { email = it },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (mensaje != null) {
                Text(
                    text = mensaje!!,
                    color = if (esExito) Color.Green else Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                BotonPrimero(
                    text = "Send Reset Link",
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
                    onClick = {
                        if (email.isBlank()) {
                            mensaje = "Please enter your email"
                            esExito = false
                        } else {
                            isLoading = true
                            FirebaseAuth.getInstance()
                                .sendPasswordResetEmail(email)
                                .addOnSuccessListener {
                                    isLoading = false
                                    mensaje = "Reset link sent. Check your email"
                                    esExito = true
                                }
                                .addOnFailureListener { error ->
                                    isLoading = false
                                    mensaje = "Error: ${error.message}"
                                    esExito = false
                                }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BotonPrimero(
                text = "Back to Login",
                modifier = Modifier
                    .width(240.dp)
                    .height(50.dp),
                onClick = onBack,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}