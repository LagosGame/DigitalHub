package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetAllMazosUseCase
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.presentation.viewmodel.ListaMazosViewModel

class ListaMazosViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaMazosViewModel::class.java)) {
            val mazoRepository = MazoRepositoryFirestoreImpl()
            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository, authRepository)
            val getUserByIdUseCase = GetUserByIdUseCase(userRepository)

            val getAllMazosUseCase = GetAllMazosUseCase(mazoRepository)
            val createMazoUseCase = CrearMazoUseCase(mazoRepository)

            val cartaDataSource = FakeCartaDataSource()
            val cartaRepository = CartaRepositoryImpl(cartaDataSource)
            val getCartasUseCase = GetCartasUseCase(cartaRepository)


            @Suppress("UNCHECKED_CAST")
            return ListaMazosViewModel(
                getAllMazosUseCase = getAllMazosUseCase,
                getCartasUseCase = getCartasUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase,
                getUserByIdUseCase = getUserByIdUseCase,
                createMazoUseCase = createMazoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}