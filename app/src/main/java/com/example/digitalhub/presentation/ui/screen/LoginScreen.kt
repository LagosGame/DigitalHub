package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.data.local.PreferencesManager
import com.example.digitalhub.di.LoginViewModelFactory
import com.example.digitalhub.presentation.ui.components.FondoPrincipal
import com.example.digitalhub.presentation.ui.components.LoginContentido
import com.example.digitalhub.presentation.viewmodel.EventosNavegacion
import com.example.digitalhub.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory()),
    onLoginSuccess : (username: String) -> Unit,
    onRegisterSuccess: () -> Unit,
    onNavigateToRecuperarPassword: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
    val context = LocalContext.current
    //Navegacion eventos//

    LaunchedEffect(Unit) {
        val prefs = PreferencesManager(context)
        if (prefs.shouldRemember()) {
            viewModel.cargarCredencialesGuardadas(
                username = prefs.getSavedUsername() ?: "",
                password = prefs.getSavedPassword() ?: ""
            )
        }
    }

    LaunchedEffect(navigationEvent) {
        when (val event = navigationEvent) {
            is EventosNavegacion.NavegarAMain -> {
                val prefs = PreferencesManager(context)
                if (uiState.recordarCredenciales) {
                    prefs.saveCredentials(uiState.username, uiState.password)
                } else {
                    prefs.clearCredentials()
                }

                viewModel.navegacionCompleta()
                onLoginSuccess(event.username)
            }
            is EventosNavegacion.NavegarARegistro -> {
                viewModel.navegacionCompleta()
                onRegisterSuccess()
            }
            is EventosNavegacion.NavegarARecuperarPassword -> {
                viewModel.navegacionCompleta()
                onNavigateToRecuperarPassword()
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
        onRegisterClick = viewModel::onRegisterClick,
        onRememberChange = viewModel::toggleRecordar,
        onRecuperarPasswordClick = viewModel::onRecuperarPasswordClick
    )

}