package com.example.digitalhub.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.PerfilViewModelFactory
import com.example.digitalhub.presentation.ui.components.PerfilContentido
import com.example.digitalhub.presentation.viewmodel.PerfilNavigationEvent
import com.example.digitalhub.presentation.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    userId: String?,
    viewModel: PerfilViewModel = viewModel(factory = PerfilViewModelFactory(userId)),
    onBack: () -> Unit,
    onEditarPerfil: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onVerMazo: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val esPropio = userId ==null
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {

            is PerfilNavigationEvent.NavegarALogin -> {

                viewModel.clearNavigationEvent()
                onNavigateToLogin()
            }
            null -> {}
        }
    }

    when {
        uiState.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        uiState.errorMessage != null -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = uiState.errorMessage ?: "Error",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        uiState.usuario != null -> {
            PerfilContentido(
                usuario = uiState.usuario!!,
                mazosPropios = uiState.mazosPropios,
                onBack = onBack,
                esPropio = esPropio,
                todasLasCartas = uiState.todasLasCartas,
                onLogoutClick = { viewModel.onLogoutClick() } ,
                onVerMazo = onVerMazo,
                onUpdateBiografia = { newBiografia ->
                    viewModel.updateBiografia(newBiografia)
                },
                onUpdateCumpleanos = { newCumpleanos ->
                    viewModel.updateCumpleanos(newCumpleanos)
                },
                onUpdateColorFavorito = { newColor ->
                    viewModel.updateColorFavorito(newColor)
                },
                onUpdateAvatar = { newAvatarId ->
                    viewModel.updateAvatar(newAvatarId)
                },
                isSaving = uiState.isSaving
            )
        }
    }
}