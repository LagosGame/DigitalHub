package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.usecase.EliminarMazoUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.presentation.ui.state.ConstruirMazoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConstruirMazoViewModel(
    private val getMazosUseCase: GetMazosUseCase,
    private val eliminarMazoUseCase: EliminarMazoUseCase
): ViewModel()
{
    private val _uiState = MutableStateFlow(ConstruirMazoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMazos()
    }
    fun fetchMazos(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val mazos = getMazosUseCase()
                _uiState.update {
                    it.copy(
                        mazos = mazos,
                        isLoading = false
                    )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error fetching decks: ${e.message}"
                    )
                }
            }
        }
    }

    fun eliminarMazo(mazoId: String) {
        viewModelScope.launch {
            try {
                eliminarMazoUseCase(mazoId)
                fetchMazos()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error deleting deck: ${e.message}")
                }
            }
        }
    }
}