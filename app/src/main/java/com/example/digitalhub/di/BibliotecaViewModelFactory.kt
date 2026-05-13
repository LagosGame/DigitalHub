package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.BibliotecaRepositoryImpl
import com.example.digitalhub.domain.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.FiltrarCartasUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.presentation.viewmodel.BibliotecaViewModel

class BibliotecaViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BibliotecaViewModel::class.java)) {
            val fakeDataSource = FakeCartaDataSource()
            val bibliotecaRepository = BibliotecaRepositoryImpl()
            val cartaRepository = CartaRepositoryImpl(fakeDataSource, bibliotecaRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository, authRepository)

            @Suppress("UNCHECKED_CAST")
            return BibliotecaViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                cartaRepository = cartaRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}