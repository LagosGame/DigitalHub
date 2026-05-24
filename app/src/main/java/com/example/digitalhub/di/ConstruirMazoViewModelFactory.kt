package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.EliminarMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.viewmodel.ConstruirMazoViewModel

class ConstruirMazoViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConstruirMazoViewModel::class.java)){
            val mazoRepository = MazoRepositoryFirestoreImpl()

            val userRepository = UserRepositoryImpl()
            val autentificacionRepository = AutentificacionRepositoryImpl()

            val getCurrentUserUseCase = GetCurrentUserUseCase(
                userRepository = userRepository,
                authRepository = autentificacionRepository
            )

            val getMazosUseCase = GetMazosUseCase(mazoRepository, getCurrentUserUseCase)
            val eliminarMazoUseCase = EliminarMazoUseCase(mazoRepository)


            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
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