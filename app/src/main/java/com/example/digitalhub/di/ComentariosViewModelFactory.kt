package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.repository.MazoRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.ComentarioRepositoryImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.AgregarComentarioUseCase
import com.example.digitalhub.domain.usecase.GetComentariosUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.domain.usecase.ToggleLikeComentarioUseCase
import com.example.digitalhub.presentation.viewmodel.ComentariosViewModel

class ComentariosViewModelFactory(
    private val mazoId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComentariosViewModel::class.java)) {
            val mazoRepository = MazoRepositoryFirestoreImpl()
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)

            val comentarioRepository = ComentarioRepositoryImpl()
            val getComentariosUseCase = GetComentariosUseCase(comentarioRepository)
            val agregarComentarioUseCase = AgregarComentarioUseCase(comentarioRepository)
            val toggleLikeUseCase = ToggleLikeComentarioUseCase(comentarioRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository,authRepository)
            val getUserByIdUseCase = GetUserByIdUseCase(userRepository)


            @Suppress("UNCHECKED_CAST")
            return ComentariosViewModel(
                getMazoByIdUseCase = getMazoByIdUseCase,
                getComentariosUseCase = getComentariosUseCase,
                agregarComentarioUseCase = agregarComentarioUseCase,
                toggleLikeComentarioUseCase = toggleLikeUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase,
                getUserByIdUseCase = getUserByIdUseCase,
                mazoId = mazoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}