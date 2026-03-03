package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.ActualizarMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.viewmodel.DetalleMazoViewModel

class DetalleMazoViewModelFactory(
    private val mazoId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleMazoViewModel::class.java)) {
            val fakeCartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(fakeCartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            val fakeMazoDataSource = FakeMazoDataSource()
            val mazoRepository = MazoRepositoryImpl(fakeMazoDataSource)
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)
            val actualizarMazoUseCase = ActualizarMazoUseCase(mazoRepository)

            @Suppress("UNCHECKED_CAST")
            return DetalleMazoViewModel(
                getMazoByIdUseCase = getMazoByIdUseCase,
                getCartasUseCase = getCartasUseCase,
                actualizarMazoUseCase = actualizarMazoUseCase,
                mazoId = mazoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}