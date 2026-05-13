package com.example.digitalhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.usecase.GetCartasUseCase
import com.example.digitalhub.domain.usecase.GetCurrentUserUseCase
import com.example.digitalhub.domain.usecase.GetMazosUseCase
import com.example.digitalhub.domain.usecase.GetUserByIdUseCase
import com.example.digitalhub.domain.usecase.LogoutUseCase
import com.example.digitalhub.domain.usecase.UpdateUserUseCase
import com.example.digitalhub.presentation.ui.state.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getMazosUseCase: GetMazosUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getCartasUseCase: GetCartasUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userId: String?
) : ViewModel() {
    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<PerfilNavigationEvent?>(null)
    val navigationEvent: StateFlow<PerfilNavigationEvent?> = _navigationEvent.asStateFlow()

    init {
        cargarPerfil()
    }
    private fun cargarPerfil() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val usuario = if (userId != null) {
                    getUserByIdUseCase(userId)
                } else {
                    getCurrentUserUseCase()
                }
                val todosMazos = getMazosUseCase()
                val todasLasCartas = getCartasUseCase()
                val mazosPropios = todosMazos.filter { it.userId == usuario?.id }
                _uiState.update {
                    it.copy(
                        usuario = usuario,
                        mazosPropios = mazosPropios,
                        todasLasCartas = todasLasCartas,
                        isLoading = false,
                        isSaving = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    fun updateBiografia(newBiografia: String) {
        val currentUser = _uiState.value.usuario ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val updatedUser = currentUser.copy(biografia = newBiografia)

            updateUserUseCase(updatedUser)
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            usuario = updatedUser,
                            isSaving = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    fun updateCumpleanos(newCumpleanos: String) {
        val currentUser = _uiState.value.usuario ?: return

        viewModelScope.launch {

            _uiState.update { it.copy(isSaving = true) }

            val updatedUser = currentUser.copy(cumpleanos = newCumpleanos)

            updateUserUseCase(updatedUser)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            usuario = updatedUser,
                            isSaving = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    fun updateColorFavorito(newColor: ColorCarta?) {
        val currentUser = _uiState.value.usuario ?: return

        viewModelScope.launch {

            _uiState.update { it.copy(isSaving = true) }

            val updatedUser = currentUser.copy(colorFavorito = newColor)

            updateUserUseCase(updatedUser)
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            usuario = updatedUser,
                            isSaving = false
                        )
                    }
                }
                .onFailure { error ->

                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }
    fun onLogoutClick() {
        viewModelScope.launch {
            logoutUseCase()
            _navigationEvent.value = PerfilNavigationEvent.NavegarALogin
        }
    }
    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
    fun updateAvatar(newAvatarId: Int) {
        val currentUser = _uiState.value.usuario ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val updatedUser = currentUser.copy(iconoId = newAvatarId)

            updateUserUseCase(updatedUser)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            usuario = updatedUser,
                            isSaving = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }
}
sealed class PerfilNavigationEvent {
    object NavegarALogin : PerfilNavigationEvent()
}