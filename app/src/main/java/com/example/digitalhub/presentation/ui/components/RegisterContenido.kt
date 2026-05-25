package com.example.digitalhub.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    FondoSecundario()
    if (isLandscape) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LetrasBordes(
                    text = "REGISTER",
                    fontSize = 48.sp,
                    fontFamily = DigitalFont,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Blue,
                    strokeWidth = 10f,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CamposRegister(
                    uiState = uiState,
                    onUsernameChange = onUsernameChange,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    onRegisterClick = onRegisterClick,
                    onBackClick = onBackClick
                )
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
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
                TextoError(uiState.usernameError)

                //Email
                TextfieldUsuario(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    placeholder = "Email",
                    onValueChange = onEmailChange,
                    enabled = !uiState.isLoading
                )
                TextoError(uiState.emailError)

                //Contraseña
                TextfieldUsuario(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    placeholder = "Password",
                    onValueChange = onPasswordChange,
                    isPassword = true,
                    enabled = !uiState.isLoading
                )
                TextoError(uiState.passwordError)

                //Confirmar Contraseña
                TextfieldUsuario(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.confirmPassword,
                    placeholder = "Confirm Password",
                    onValueChange = onConfirmPasswordChange,
                    isPassword = true,
                    enabled = !uiState.isLoading
                )
                TextoError(uiState.confirmPasswordError)


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
                        enabled = !uiState.isLoading,
                        onClick = onRegisterClick
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                //Volver
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
                        text = "Back to Login",
                        onClick = onBackClick
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