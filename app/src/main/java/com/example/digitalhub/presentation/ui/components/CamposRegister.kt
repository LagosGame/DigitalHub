package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalhub.presentation.ui.state.RegisterUiState

@Composable
fun CamposRegister(
    uiState: RegisterUiState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TextfieldUsuario(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.username,
        placeholder = "Username",
        onValueChange = onUsernameChange,
        enabled = !uiState.isLoading
    )
    TextoError(uiState.usernameError)
    Spacer(modifier = Modifier.height(8.dp))
    TextfieldUsuario(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.email,
        placeholder = "Email",
        onValueChange = onEmailChange,
        enabled = !uiState.isLoading
    )
    TextoError(uiState.emailError)
    Spacer(modifier = Modifier.height(8.dp))
    TextfieldUsuario(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.password,
        placeholder = "Password",
        onValueChange = onPasswordChange,
        isPassword = true,
        enabled = !uiState.isLoading
    )
    TextoError(uiState.passwordError)
    Spacer(modifier = Modifier.height(8.dp))
    TextfieldUsuario(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.confirmPassword,
        placeholder = "Confirm Password",
        onValueChange = onConfirmPasswordChange,
        isPassword = true,
        enabled = !uiState.isLoading
    )
    TextoError(uiState.confirmPasswordError)

    Spacer(modifier = Modifier.height(8.dp))

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
}