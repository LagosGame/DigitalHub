package com.example.digitalhub.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
    onRememberChange: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onRecuperarPasswordClick: () -> Unit
){
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    //Fondo
    FondoPrincipal()
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
                    text = "DIGITAL\nHUB",
                    fontSize = 52.sp,
                    fontFamily = DigitalFont,
                    fontWeight = FontWeight.Normal,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 20f,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                key("subtitulo_land") {
                    LetrasBordes(
                        "Create, share and get better\nat DigimonTCG",
                        fontSize = 15.sp,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Normal,
                        textColor = Color.White,
                        strokeColor = Color.Blue,
                        strokeWidth = 5f,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormularioLogin(
                    uiState = uiState,
                    onUsernameChange = onUsernameChange,
                    onPasswordChange = onPasswordChange,
                    onRememberChange = onRememberChange,
                    onLoginClick = onLoginClick,
                    onRegisterClick = onRegisterClick,
                    onRecuperarPasswordClick = onRecuperarPasswordClick
                )
            }
        }
    }else{
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
                        "Create, share and get better at DigimonTCG",
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = uiState.recordarCredenciales,
                            onCheckedChange = { onRememberChange() },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF1565C0),
                                uncheckedColor = Color.White
                            ),
                            enabled = !uiState.isLoading
                        )
                        Text(
                            text = "Remember me",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = Roboto,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                    if (uiState.errorMessage != null) {
                        Text(
                            text = uiState.errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    }

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
                        text = "Register",
                        onClick = onRegisterClick
                    )
                    Box(
                        modifier = Modifier.clickable {
                            onRecuperarPasswordClick()
                        }
                    ) {
                        key("subtitulo") {
                            //Texto que nos redirige a RegistroScreen onClick//
                            LetrasBordes(
                                "Forgot password?",
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
        onRememberChange = {},
        onRecuperarPasswordClick = {}
    )
}