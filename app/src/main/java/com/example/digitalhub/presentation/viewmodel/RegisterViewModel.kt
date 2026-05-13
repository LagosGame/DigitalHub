package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.CheckUsuarioUseCase
import com.example.digitalhub.domain.usecase.RegisterUseCase
import com.example.digitalhub.presentation.ui.state.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val checkUsuarioUseCase: CheckUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<RegisterNavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { state ->
            state.copy(
                username = username,
                usernameError = if (username.isBlank()) "Username cannot be blank" else null,
                errorMessage = null
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { state ->
            state.copy(
                email = email,
                emailError = when {
                    email.isBlank() -> "Email cannot be blank"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email"
                    else -> null
                },
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { state ->
            state.copy(
                password = password,
                passwordError = when {
                    password.isBlank() -> "Password cannot be blank"
                    password.length < 6 -> "At least 6 characters"
                    else -> null
                },
                confirmPasswordError = if (
                    state.confirmPassword.isNotBlank() &&
                    state.confirmPassword != password
                ) "Passwords don't match" else null,
                errorMessage = null
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { state ->
            state.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = when {
                    confirmPassword.isBlank() -> "Confirm your password"
                    confirmPassword != state.password -> "Passwords don't match"
                    else -> null
                },
                errorMessage = null
            )
        }
    }

    fun onRegisterClick() {
        val currentState = _uiState.value

        val usernameError = if (currentState.username.isBlank()) "Username cannot be blank" else null
        val emailError = when {
            currentState.email.isBlank() -> "Email cannot be blank"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches() -> "Invalid email"
            else -> null
        }
        val passwordError = when {
            currentState.password.isBlank() -> "Password cannot be blank"
            currentState.password.length < 6 -> "At least 6 characters"
            else -> null
        }
        val confirmPasswordError = when {
            currentState.confirmPassword.isBlank() -> "Confirm your password"
            currentState.confirmPassword != currentState.password -> "Passwords don't match"
            else -> null
        }

        if (usernameError != null || emailError != null ||
            passwordError != null || confirmPasswordError != null) {
            _uiState.update {
                it.copy(
                    usernameError = usernameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {

                val isAvailable = checkUsuarioUseCase(currentState.username)

                if (!isAvailable) {
                    println("Username '${currentState.username}' already taken")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            usernameError = "Username already taken"
                        )
                    }
                    return@launch
                }
                // Registrar usuario
                registerUseCase(
                    username = currentState.username,
                    email = currentState.email,
                    password = currentState.password,
                    confirmPassword = currentState.confirmPassword
                )
                    .onSuccess { user ->
                        _uiState.update { it.copy(isLoading = false) }
                        _navigationEvent.value = RegisterNavigationEvent.NavegarAHome
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.message ?: "Unknown error"
                            )
                        }
                    }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
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
}

sealed class RegisterNavigationEvent {
    object NavegarAHome : RegisterNavigationEvent()
    object NavegarAtras : RegisterNavigationEvent()
}