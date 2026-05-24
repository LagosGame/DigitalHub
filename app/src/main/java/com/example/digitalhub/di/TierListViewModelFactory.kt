package com.example.digitalhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalhub.data.local.FakeArquetipoDataSource
import com.example.digitalhub.data.repository.ArquetipoRepositoryImpl
import com.example.digitalhub.domain.usecase.GetArquetiposUseCase
import com.example.digitalhub.presentation.viewmodel.TierListViewModel

class TierListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TierListViewModel::class.java)) {
            val dataSource = FakeArquetipoDataSource()
            val repository = ArquetipoRepositoryImpl(dataSource)
            val getArquetiposUseCase = GetArquetiposUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return TierListViewModel(getArquetiposUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}