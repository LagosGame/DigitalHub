package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    //Fondo
    FondoPrincipal()

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(200.dp))
            //Texto en grande//
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
                contentAlignment = Alignment.Center,

                )   {
                LetrasBordes(
                    text = "DIGITAL\nHUB",
                    fontSize = 60.sp,
                    fontFamily = DigitalFont,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 20f,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.fillMaxWidth().weight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp))
            {
                key("subtitulo") {
                //Texto pequeño//
                LetrasBordes(
                    "Crea, comparte y mejora en Digimon TCG",
                    fontSize = 18.sp,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Blue,
                    strokeWidth = 5f,
                    textAlign = TextAlign.Center

                )
                }
                //Textfield//
                TextfieldUsuario(
                    value = uiState.username,
                    placeholder = "Username",
                    onValueChange = onUsernameChange,
                    enabled = !uiState.isLoading
                )
                //Textfield//
                TextfieldUsuario(
                    value = uiState.password,
                    placeholder = "Password",
                    onValueChange = onPasswordChange,
                    isPassword = true,
                    enabled = !uiState.isLoading
                )

                //Boton//
                BotonPrimero (
                    modifier = Modifier.width(240.dp).height(50.dp),
                    text = "Login",
                    enabled=uiState.isLoginEnabled&& !uiState.isLoading,
                    onClick = onLoginClick
                )
                //Boton//
                BotonPrimero (modifier = Modifier.width(240.dp).height(50.dp),
                    enabled = !uiState.isLoading,
                    text = "Login with Google",
                    onClick = onGoogleLoginClick
                )
                Box(
                    modifier = Modifier.clickable {
                        onRegisterClick()
                    }
                ) {
                    key("subtitulo") {
                        //Texto que nos redirige a RegistroScreen onClick//
                        LetrasBordes(
                            "¿No tienes cuenta? \n Registrate",
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
        }
    }
}

@Preview
@Composable
fun LoginPreview(){
    LoginContentido(
        uiState = LoginUiState(),
        onUsernameChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onGoogleLoginClick = {},
        onRegisterClick =  {},
    )
}