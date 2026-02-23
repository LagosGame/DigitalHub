package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.usecase.FiltrarCartasUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.presentation.viewmodel.BibliotecaViewModel

class BibliotecaViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BibliotecaViewModel::class.java)) {
            val fakeDataSource = FakeCartaDataSource()
            val repository = CartaRepositoryImpl(fakeDataSource)
            val getCartasUseCase = GetCartasUseCase(repository)
            val filtrarCartasUseCase = FiltrarCartasUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return BibliotecaViewModel(
                getCartasUseCase = getCartasUseCase,
                filtrarCartasUseCase = filtrarCartasUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}