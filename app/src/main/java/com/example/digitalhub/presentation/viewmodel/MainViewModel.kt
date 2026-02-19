package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.digitalhub.data.local.noticias
import com.example.digitalhub.presentation.ui.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun setIndice(nuevoIndice: Int) {
        _uiState.update { it.copy(indiceSlider = nuevoIndice) }
    }
    fun siguiente() {
        _uiState.update {
            val nuevoIndice = if (it.indiceSlider < noticias.lastIndex) {
                it.indiceSlider + 1
            } else {
                0
            }
            it.copy(indiceSlider = nuevoIndice)
        }
    }
    fun anterior() {
        _uiState.update {
            val nuevoIndice = if (it.indiceSlider > 0) {
                it.indiceSlider - 1
            } else {
                noticias.lastIndex
            }
            it.copy(indiceSlider = nuevoIndice)
        }
    }
}