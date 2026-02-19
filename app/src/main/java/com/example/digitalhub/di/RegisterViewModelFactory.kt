package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeAuthDataSource
import com.example.digitalhub.data.repository.AuthRepositoryImpl
import com.example.digitalhub.domain.usecase.RegisterUseCase
import com.example.digitalhub.presentation.viewmodel.RegisterViewModel

class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            val fakeDataSource = FakeAuthDataSource()
            val repository = AuthRepositoryImpl(fakeDataSource)
            val registerUseCase = RegisterUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(registerUseCase) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class : ${modelClass.name}")
    }
}