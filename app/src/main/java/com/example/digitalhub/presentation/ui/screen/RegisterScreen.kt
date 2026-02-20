package com.example.digitalhub.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.RegisterViewModelFactory
import com.example.digitalhub.presentation.ui.components.RegisterContentido
import com.example.digitalhub.presentation.viewmodel.RegisterNavigationEvent
import com.example.digitalhub.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory()),
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

    //Nav
    LaunchedEffect(navigationEvent) {
        when (val event = navigationEvent) {
            is RegisterNavigationEvent.NavegarAHome -> {
                onRegisterSuccess()
                viewModel.navegacionCompleta()
            }
            RegisterNavigationEvent.NavegarAtras -> {
                onBack()
                viewModel.navegacionCompleta()
            }
            null -> {}
        }
    }

    //UI
    RegisterContentido(
        uiState = uiState,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = viewModel::onRegisterClick,
        onBackClick = viewModel::onBackClick
    )
}