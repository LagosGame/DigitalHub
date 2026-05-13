package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.domain.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.domain.repository.CartaRepositoryImpl
import com.example.digitalhub.domain.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.presentation.viewmodel.VerMazoViewModel

class VerMazoViewModelFactory(
    private val mazoId: String
):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerMazoViewModel::class.java)) {
            val mazoRepository = MazoRepositoryFirestoreImpl()
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)
            val crearMazoUseCase = CrearMazoUseCase(mazoRepository)

            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
            val getCurrentUserUseCase = GetCurrentUserUseCase( userRepository,authRepository)

            @Suppress("UNCHECKED_CAST")
            return VerMazoViewModel(
                getMazoByIdUseCase = getMazoByIdUseCase,
                getCartasUseCase = getCartasUseCase,
                getUserByIdUseCase = getUserByIdUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase,
                crearMazoUseCase = crearMazoUseCase,
                mazoId = mazoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}