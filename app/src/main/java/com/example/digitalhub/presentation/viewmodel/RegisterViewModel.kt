package com.example.digitalhub.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.usecase.RegisterUseCase
import com.example.digitalhub.presentation.ui.state.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<RegisterNavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()


    fun onUsernameChange(username: String){
        _uiState.update { state ->
            state.copy(
                username = username,
                isRegisterEnabled = validarCampos(
                    username,
                    state.email,
                    state.password,
                    state.confirmPassword

                ),
                errorMessage = null
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { state ->
            state.copy(
                email = email,
                isRegisterEnabled = validarCampos(
                    state.username,
                    email,
                    state.password,
                    state.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { state ->
            state.copy(
                password = password,
                isRegisterEnabled = validarCampos(
                    state.username,
                    state.email,
                    password,
                    state.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { state ->
            state.copy(
                confirmPassword = confirmPassword,
                isRegisterEnabled = validarCampos(
                    state.username,
                    state.email,
                    state.password,
                    confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    fun onRegisterClick(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = registerUseCase(
                username = _uiState.value.username,
                email = _uiState.value.email,
                password = _uiState.value.password,
                confirmPassword = _uiState.value.confirmPassword
            )
            _uiState.update { it.copy(isLoading = false) }

            when(result){
                is AutentificaciónResultado.Correcto->{
                    _navigationEvent.value = RegisterNavigationEvent.NavegarAMain(result.user.username)
                }
                is AutentificaciónResultado.Incorrecto -> {
                    _uiState.update { it.copy(errorMessage = result.mensaje) }
                }
            }
        }
    }

    fun onBackClick() {
        _navigationEvent.value = RegisterNavigationEvent.NavegarAtras
    }
    fun navegacionCompleta() {
        _navigationEvent.value = null
    }

    private fun validarCampos(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return username.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                password == confirmPassword
    }
}
sealed class RegisterNavigationEvent {
    data class NavegarAMain(val username: String) : RegisterNavigationEvent()
    object NavegarAtras : RegisterNavigationEvent()
}