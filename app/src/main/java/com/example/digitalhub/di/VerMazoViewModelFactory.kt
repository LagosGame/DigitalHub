package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.viewmodel.VerMazoViewModel

class VerMazoViewModelFactory(
    private val mazoId: String
):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerMazoViewModel::class.java)) {
            val mazoDataSource = FakeMazoDataSource()
            val mazoRepository = MazoRepositoryImpl(mazoDataSource)
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)

            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            @Suppress("UNCHECKED_CAST")
            return VerMazoViewModel(
                getMazoByIdUseCase = getMazoByIdUseCase,
                getCartasUseCase = getCartasUseCase,
                mazoId = mazoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}