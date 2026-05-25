package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.GetAllMazosUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.domain.usecase.LogoutUseCase
import com.example.digitalhub.domain.usecase.UpdateUserUseCase
import com.example.digitalhub.presentation.viewmodel.PerfilViewModel

class PerfilViewModelFactory(
    private val userId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {

            val userRepository = UserRepositoryImpl()
            val authRepository = AutentificacionRepositoryImpl()
            val mazoRepository = MazoRepositoryFirestoreImpl()

            val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
            val logoutUseCase = LogoutUseCase(authRepository)
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository, authRepository)
            val getMazosUseCase = GetMazosUseCase(mazoRepository, getCurrentUserUseCase)
            val getAllMazosUseCase = GetAllMazosUseCase(mazoRepository)
            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)
            val updateUserUseCase = UpdateUserUseCase(userRepository)

            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(
                getUserByIdUseCase = getUserByIdUseCase,
                getMazosUseCase = getMazosUseCase,
                getAllMazosUseCase = getAllMazosUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase,
                logoutUseCase = logoutUseCase,
                getCartasUseCase = getCartasUseCase,
                updateUserUseCase = updateUserUseCase,
                userId = userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}