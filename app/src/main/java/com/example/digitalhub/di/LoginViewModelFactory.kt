package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.domain.usecase.LoginGoogleCase
import com.example.digitalhub.domain.usecase.LoginUseCase
import com.example.digitalhub.presentation.viewmodel.LoginViewModel

class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){

            val repository = AutentificacionRepositoryImpl()
            val loginUseCase = LoginUseCase(repository)
            val loginGoogleCase = LoginGoogleCase(repository)

            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                loginUseCase = loginUseCase,
                loginGoogleCase = loginGoogleCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}