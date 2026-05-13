package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.AutentificaciónResultado
import com.example.digitalhub.domain.usecase.LoginGoogleCase
import com.example.digitalhub.domain.usecase.LoginUseCase
import com.example.digitalhub.presentation.ui.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginGoogleCase: LoginGoogleCase
): ViewModel()
{
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<EventosNavegacion?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()


    fun onUsernameChange(username : String){
        _uiState.update { state ->
            state.copy(
                username = username,
                isLoginEnabled = validarCampos(username,state.password),
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String){
        _uiState.update { state ->
            state.copy(
                password = password,
                isLoginEnabled = validarCampos(state.username,password),
                errorMessage = null
            )
        }
    }

    fun onLoginClick(){
        val currentState = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            loginUseCase(currentState.username, currentState.password)
                .onSuccess { user ->

                    println("Login successful")
                    _uiState.update { it.copy(isLoading = false) }
                    _navigationEvent.value = EventosNavegacion.NavegarAMain(user.username)
                }
                .onFailure { error ->
                    println("Login error: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unknown error"
                        )
                    }
                }
        }
    }

    fun onGoogleLoginClick(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = loginGoogleCase()

            _uiState.update { it.copy(isLoading = false) }

            when(result){
                is AutentificaciónResultado.Correcto ->{
                    _navigationEvent.value = EventosNavegacion.NavegarAMain(result.user.username)
                }
                is AutentificaciónResultado.Incorrecto -> {
                    _uiState.update { it.copy(errorMessage = result.mensaje) }
                }
            }
        }
    }

    fun onRegisterClick(){
        _navigationEvent.value = EventosNavegacion.NavegarARegistro
    }

    fun validarCampos(username : String, password : String): Boolean{
        return username.isNotBlank() && password.isNotBlank()
    }

    fun navegacionCompleta(){
        _navigationEvent.value = null
    }
    fun toggleRecordar() {
        _uiState.update { it.copy(recordarCredenciales = !it.recordarCredenciales) }
    }

    fun cargarCredencialesGuardadas(username: String, password: String) {
        _uiState.update {
            it.copy(
                username = username,
                password = password,
                recordarCredenciales = true,
                isLoginEnabled = validarCampos(username, password)
            )
        }
    }
    fun onRecuperarPasswordClick() {
        _navigationEvent.value = EventosNavegacion.NavegarARecuperarPassword
    }

}
sealed class EventosNavegacion{
    data class NavegarAMain(val username: String): EventosNavegacion()
    object NavegarARegistro : EventosNavegacion()
    object NavegarARecuperarPassword : EventosNavegacion()
}