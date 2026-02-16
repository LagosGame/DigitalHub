package com.example.digitalhub.presentation.ui.state

data class LoginUiState(
    val username : String ="",
    val password : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val isLoginEnabled : Boolean = false
)
