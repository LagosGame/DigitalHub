package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.Tier
import com.example.digitalhub.domain.usecase.GetArquetiposUseCase
import com.example.digitalhub.presentation.ui.state.TierListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TierListViewModel(
    private val getArquetiposUseCase: GetArquetiposUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(TierListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        cargarArquetipos()
    }

    private fun cargarArquetipos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val arquetipos = getArquetiposUseCase()
                _uiState.update {
                    it.copy(
                        arquetipos = arquetipos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    fun getArquetiposPorTier(tier: Tier) = _uiState.value.arquetipos.filter { it.tier == tier }
}