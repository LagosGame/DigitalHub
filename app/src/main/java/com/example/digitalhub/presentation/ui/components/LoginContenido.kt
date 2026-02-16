package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.LoginUiState
import com.example.digitalhub.ui.theme.DigitalFont
import com.example.digitalhub.ui.theme.Roboto

@Composable
fun LoginContentido(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        FondoPrincipal()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Título
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(top = 150.dp),
                contentAlignment = Alignment.Center
            ) {
                LetrasBordes(
                    text = "DIGITAL\nHUB",
                    fontSize = 60.sp,
                    fontFamily = DigitalFont,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 15f,
                    textAlign = TextAlign.Center
                )
            }

            //Formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                LetrasBordes(
                    text = "Create, share and become a better player in DigimonTCG",
                    fontSize = 18.sp,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Blue,
                    strokeWidth = 5f,
                    textAlign = TextAlign.Center
                )

                TextfieldUsuario(
                    value = uiState.username,
                    placeholder = "Username",
                    onValueChange = onUsernameChange,
                    enabled = !uiState.isLoading
                )

                TextfieldUsuario(
                    value = uiState.password,
                    placeholder = "Password",
                    onValueChange = onPasswordChange,
                    isPassword = true,
                    enabled = !uiState.isLoading
                )

                if (uiState.errorMessage != null) {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                if (!uiState.isLoading && uiState.errorMessage == null) {
                    Text(
                        text = "Prueba con:\nUsuario: demo\nContraseña: 123456",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                BotonEntrar(
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
                    text = if (uiState.isLoading) "Loading..." else "Enter",
                    enabled = uiState.isLoginEnabled && !uiState.isLoading,
                    onClick = onLoginClick
                )

                BotonEntrar(
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
                    enabled = !uiState.isLoading,
                    text = "Enter with Google",
                    onClick = onGoogleLoginClick
                )

                Box(
                    modifier = Modifier.clickable(enabled = !uiState.isLoading) {
                        onRegisterClick()
                    }
                ) {
                    LetrasBordes(
                        text = "¿You don't have an account?\nRegister",
                        fontSize = 18.sp,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Normal,
                        textColor = Color.White,
                        strokeColor = Color.Blue,
                        strokeWidth = 5f,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}