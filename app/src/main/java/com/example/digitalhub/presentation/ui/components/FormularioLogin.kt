package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.digitalhub.ui.theme.Roboto
@Composable
fun FormularioLogin(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberChange: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onRecuperarPasswordClick: () -> Unit
) {
    TextfieldUsuario(
        value = uiState.username,
        placeholder = "Username",
        onValueChange = onUsernameChange,
        enabled = !uiState.isLoading
    )
    Spacer(modifier = Modifier.height(20.dp))
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
    BotonPrimero(
        modifier = Modifier.width(240.dp).height(50.dp),
        text = "Login",
        enabled = uiState.isLoginEnabled && !uiState.isLoading,
        onClick = onLoginClick
    )
    Spacer(modifier = Modifier.height(20.dp))
    BotonPrimero(
        modifier = Modifier.width(240.dp).height(50.dp),
        enabled = !uiState.isLoading,
        text = "Register",
        onClick = onRegisterClick
    )
    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.clickable { onRecuperarPasswordClick() }) {
        key("subtitulo") {
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