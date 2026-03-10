package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.EliminarMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.viewmodel.ConstruirMazoViewModel

class ConstruirMazoViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConstruirMazoViewModel::class.java)){
            val fakeDataSource = FakeMazoDataSource()
            val repository = MazoRepositoryImpl(fakeDataSource)
            val getMazosUseCase = GetMazosUseCase(repository)
            val eliminarMazoUseCase = EliminarMazoUseCase(repository)
            val fakeCartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(fakeCartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            @Suppress("UNCHECKED_CAST")
            return ConstruirMazoViewModel(
                getMazosUseCase = getMazosUseCase,
                eliminarMazoUseCase = eliminarMazoUseCase,
                getCartasUseCase = getCartasUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}