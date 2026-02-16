package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.LoginViewModelFactory
import com.example.digitalhub.presentation.ui.components.LoginContentido
import com.example.digitalhub.presentation.viewmodel.EventosNavegacion
import com.example.digitalhub.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory()),
    onLoginSuccess : (username: String) -> Unit,
    onRegisterSuccess: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    //Navegacion eventos//

    LaunchedEffect(navigationEvent)
    {
        when(val event = navigationEvent){
            is EventosNavegacion.NavegarAMain ->{
                onLoginSuccess(event.username)
                viewModel.navegacionCompleta()
            }
            is EventosNavegacion.NavegarARegistro ->{
                onRegisterSuccess()
                viewModel.navegacionCompleta()
            }
            null -> {}
        }
    }

    //Parte grafica//
    LoginContentido(
        uiState = uiState,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick,
        onGoogleLoginClick = viewModel::onGoogleLoginClick,
        onRegisterClick = viewModel::onRegisterClick
    )

}