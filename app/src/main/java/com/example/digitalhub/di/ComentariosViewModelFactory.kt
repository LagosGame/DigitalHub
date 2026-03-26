package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.R
import com.example.digitalhub.data.local.FakeComentarioDataSource
import com.example.digitalhub.data.local.FakeMazoDataSource
import com.example.digitalhub.domain.model.User
import com.example.digitalhub.domain.repository.ComentarioRepositoryImpl
import com.example.digitalhub.domain.repository.MazoRepositoryImpl
import com.example.digitalhub.domain.usecase.AgregarComentarioUseCase
import com.example.digitalhub.domain.usecase.GetComentariosUseCase
import com.example.digitalhub.domain.usecase.GetMazoByIdUseCase
import com.example.digitalhub.domain.usecase.ToggleLikeComentarioUseCase
import com.example.digitalhub.presentation.viewmodel.ComentariosViewModel

class ComentariosViewModelFactory(
    private val mazoId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComentariosViewModel::class.java)) {
            val mazoDataSource = FakeMazoDataSource()
            val mazoRepository = MazoRepositoryImpl(mazoDataSource)
            val getMazoByIdUseCase = GetMazoByIdUseCase(mazoRepository)

            val comentarioDataSource = FakeComentarioDataSource()
            val comentarioRepository = ComentarioRepositoryImpl(comentarioDataSource)
            val getComentariosUseCase = GetComentariosUseCase(comentarioRepository)
            val agregarComentarioUseCase = AgregarComentarioUseCase(comentarioRepository)
            val toggleLikeComentarioUseCase = ToggleLikeComentarioUseCase(comentarioRepository)

            val usuarioActual = User(
                id = "user1",
                username = "Ejemplo",
                email = "yo@example.com",
                iconoId = R.drawable.ic_launcher_foreground,
            )

            @Suppress("UNCHECKED_CAST")
            return ComentariosViewModel(
                getMazoByIdUseCase = getMazoByIdUseCase,
                getComentariosUseCase = getComentariosUseCase,
                agregarComentarioUseCase = agregarComentarioUseCase,
                toggleLikeComentarioUseCase = toggleLikeComentarioUseCase,
                mazoId = mazoId,
                usuarioActual = usuarioActual
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}