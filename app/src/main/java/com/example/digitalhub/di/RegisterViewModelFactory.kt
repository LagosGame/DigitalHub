package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeAuthDataSource
import com.example.digitalhub.domain.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.domain.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.CheckUsuarioUseCase
import com.example.digitalhub.domain.usecase.RegisterUseCase
import com.example.digitalhub.presentation.viewmodel.RegisterViewModel

class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()

            val registerUseCase = RegisterUseCase(authRepository)
            val checkUsuarioUseCase = CheckUsuarioUseCase(userRepository)

            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(
                registerUseCase = registerUseCase,
                checkUsuarioUseCase = checkUsuarioUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}