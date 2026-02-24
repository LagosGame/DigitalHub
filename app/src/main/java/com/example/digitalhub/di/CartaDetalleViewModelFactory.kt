package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCartaByIdUseCase
import com.example.digitalhub.presentation.viewmodel.CartaDetalleViewModel

class CartaDetalleViewModelFactory(
    private val cartaId : String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartaDetalleViewModel::class.java)){
            val fakeDataSource = FakeCartaDataSource()
            val repository = CartaRepositoryImpl(fakeDataSource)
            val getCartaByIdUseCase = GetCartaByIdUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return CartaDetalleViewModel(
                getCartaByIdUseCase= getCartaByIdUseCase,
                cartaId = cartaId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }
}