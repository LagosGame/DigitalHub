package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.BibliotecaRepositoryImpl
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.viewmodel.CartaDetalleViewModel

class CartaDetalleViewModelFactory(
    private val cartaId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartaDetalleViewModel::class.java)) {
            val fakeDataSource = FakeCartaDataSource()
            val bibliotecaRepository = BibliotecaRepositoryImpl()
            val cartaRepository = CartaRepositoryImpl(fakeDataSource, bibliotecaRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository, authRepository)

            val mazoRepository = MazoRepositoryFirestoreImpl()
            val getMazosUseCase = GetMazosUseCase(mazoRepository,getCurrentUserUseCase)

            @Suppress("UNCHECKED_CAST")
            return CartaDetalleViewModel(
                cartaRepository = cartaRepository,
                bibliotecaRepository = bibliotecaRepository,
                mazoRepository = mazoRepository,
                getCurrentUserUseCase = getCurrentUserUseCase,
                getMazosUseCase = getMazosUseCase,
                cartaId = cartaId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }
}