package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.presentation.ui.state.RegisterUiState
import com.example.digitalhub.ui.theme.DigitalFont

@Composable
fun RegisterContentido(
    uiState: RegisterUiState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit
) {
    // 👇 Aquí pondrás tu FondoNumero2 cuando lo tengas
    FondoSecundario()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            //Título
            LetrasBordes(
                text = "REGISTER",
                fontSize = 40.sp,
                fontFamily = DigitalFont,
                fontWeight = FontWeight.Normal,
                textColor = Color.White,
                strokeColor = Color.Blue,
                strokeWidth = 10f,
                textAlign = TextAlign.Center
            )

            //Username
            TextfieldUsuario(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.username,
                placeholder = "Username",
                onValueChange = onUsernameChange,
                enabled = !uiState.isLoading
            )

            //Email
            TextfieldUsuario(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                placeholder = "Email",
                onValueChange = onEmailChange,
                enabled = !uiState.isLoading
            )

            //Contraseña
            TextfieldUsuario(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                placeholder = "Password",
                onValueChange = onPasswordChange,
                isPassword = true,
                enabled = !uiState.isLoading
            )

            // Campo Confirmar Contraseña
            TextfieldUsuario(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.confirmPassword,
                placeholder = "Confirm Password",
                onValueChange = onConfirmPasswordChange,
                isPassword = true,
                enabled = !uiState.isLoading
            )

            //Mensaje de error
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //Registrarse
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonPrimero(
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
                    text = if (uiState.isLoading) "Loading..." else "Sign in",
                    enabled = uiState.isRegisterEnabled && !uiState.isLoading,
                    onClick = onRegisterClick
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            //Google
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonPrimero(
                    modifier = Modifier
                        .width(280.dp)
                        .height(70.dp),
                    text = "Sign in with Google",
                    onClick = {}
                )
            }
        }

        //Volver
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

        //Cargando
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterContentidoPreview() {
    RegisterContentido(
        uiState = RegisterUiState(),
        onUsernameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
        onBackClick = {}
    )
}