package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.data.repository.BibliotecaRepositoryImpl
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.CartaRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.ActualizarMazoUseCase
import com.example.digitalhub.domain.usecase.CrearMazoUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.presentation.viewmodel.CrearMazoViewModel

class CrearMazoViewModelFactory(
    private val mazoId: String? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearMazoViewModel::class.java)) {
            val fakeDataSource = FakeCartaDataSource()
            val bibliotecaRepository = BibliotecaRepositoryImpl()
            val cartaRepository = CartaRepositoryImpl(fakeDataSource, bibliotecaRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository, authRepository)

            val mazoRepository = MazoRepositoryFirestoreImpl()
            val createMazoUseCase = CrearMazoUseCase(mazoRepository)
            val updateMazoUseCase = ActualizarMazoUseCase(mazoRepository)
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)
            @Suppress("UNCHECKED_CAST")
            return CrearMazoViewModel(
                cartaRepository = cartaRepository,
                getCurrentUserUseCase = getCurrentUserUseCase,
                createMazoUseCase = createMazoUseCase,
                updateMazoUseCase = updateMazoUseCase,
                getMazoByIdUseCase = getMazoByIdUseCase,
                mazoId = mazoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}