package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.repository.AutentificacionRepositoryImpl
import com.example.digitalhub.data.repository.ReportRepositoryFirestoreImpl
import com.example.digitalhub.data.repository.UserRepositoryImpl
import com.example.digitalhub.domain.usecase.EnviarReporteUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.presentation.viewmodel.MainViewModel

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val reportRepository = ReportRepositoryFirestoreImpl()
            val enviarReporteUseCase = EnviarReporteUseCase(reportRepository)

            val authRepository = AutentificacionRepositoryImpl()
            val userRepository = UserRepositoryImpl()
            val getCurrentUserUseCase = GetCurrentUserUseCase( userRepository,authRepository)

            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                enviarReporteUseCase = enviarReporteUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}