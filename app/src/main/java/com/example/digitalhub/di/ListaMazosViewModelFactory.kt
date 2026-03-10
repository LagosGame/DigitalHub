package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.viewmodel.ListaMazosViewModel

class ListaMazosViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaMazosViewModel::class.java)) {
            val dataSource = FakeMazoDataSource()
            val repository = MazoRepositoryImpl(dataSource)
            val useCase = GetMazosUseCase(repository)
            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            @Suppress("UNCHECKED_CAST")
            return ListaMazosViewModel(useCase,
                getCartasUseCase = getCartasUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}