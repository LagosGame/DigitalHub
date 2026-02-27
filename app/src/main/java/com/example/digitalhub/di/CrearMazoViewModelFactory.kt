package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.presentation.viewmodel.CrearMazoViewModel

class CrearMazoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearMazoViewModel::class.java)){
            val fakeCartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(fakeCartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            val fakeMazoDataSource = FakeMazoDataSource()
            val mazoRepository = MazoRepositoryImpl(fakeMazoDataSource)
            val crearMazoUseCase = CrearMazoUseCase(mazoRepository)

            @Suppress("UNCHECKED_CAST")
            return CrearMazoViewModel(
                getCartasUseCase = getCartasUseCase,
                crearMazoUseCase = crearMazoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}