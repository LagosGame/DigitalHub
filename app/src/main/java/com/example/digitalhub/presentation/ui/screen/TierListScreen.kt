package com.example.digitalhub.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalhub.di.TierListViewModelFactory
import com.example.digitalhub.domain.model.Tier
import com.example.digitalhub.presentation.ui.components.TierListContentido
import com.example.digitalhub.presentation.viewmodel.TierListViewModel

@Composable
fun TierListScreen(
    viewModel: TierListViewModel = viewModel(factory = TierListViewModelFactory()),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

        else -> {
            TierListContentido(
                arquetiposTier1 = viewModel.getArquetiposPorTier(Tier.TIER_1),
                arquetiposTier2 = viewModel.getArquetiposPorTier(Tier.TIER_2),
                arquetiposTier3 = viewModel.getArquetiposPorTier(Tier.TIER_3),
                onBack = onBack
            )
        }
    }
}