package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.data.local.noticias
import com.example.digitalhub.domain.model.Report
import com.example.digitalhub.domain.usecase.EnviarReporteUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.presentation.ui.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val enviarReporteUseCase: EnviarReporteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {

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
    fun abrirDialogoReportar() {
        _uiState.update { it.copy(mostrarDialogoReportar = true) }
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
    fun actualizarMensajeError(mensaje: String) {
        _uiState.update { it.copy(mensajeError = mensaje) }
    }

    fun cerrarDialogoReportar() {
        _uiState.update {
            it.copy(
                mostrarDialogoReportar = false,
                mensajeError = ""
            )
        }
    }


    fun enviarReporte() {
        val mensaje = _uiState.value.mensajeError.trim()

        if (mensaje.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val usuarioActual = getCurrentUserUseCase()

                if (usuarioActual == null) {
                    return@launch
                }

                val report = Report(
                    id = "report_${System.currentTimeMillis()}",
                    userId = usuarioActual.id,
                    tipo = "Bug",
                    descripcion = mensaje,
                    timestamp = System.currentTimeMillis()
                )

                enviarReporteUseCase(report)

                println("Report sent successfully")
                cerrarDialogoReportar()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}