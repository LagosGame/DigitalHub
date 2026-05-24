package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalhub.domain.model.Arquetipo
import com.example.digitalhub.ui.theme.Kenyan

@Composable
fun TierListContentido(
    arquetiposTier1: List<Arquetipo>,
    arquetiposTier2: List<Arquetipo>,
    arquetiposTier3: List<Arquetipo>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        FondoPrincipal()

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFD700))
                    .border(4.dp, Color.Black)
                    .padding(16.dp, top = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                LetrasBordes(
                    text = "TIER LIST - META",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Kenyan,
                    textColor = Color.White,
                    strokeColor = Color.Black,
                    strokeWidth = 8f,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (arquetiposTier1.isNotEmpty()) {
                    item {
                        TierHeader(tierNumber = 1)
                    }
                    items(arquetiposTier1) { arquetipo ->
                        ArquetipoCard(
                            arquetipo = arquetipo
                        )
                    }
                }

                if (arquetiposTier2.isNotEmpty()) {
                    item {
                        TierHeader(tierNumber = 2)
                    }
                    items(arquetiposTier2) { arquetipo ->
                        ArquetipoCard(
                            arquetipo = arquetipo
                        )
                    }
                }

                if (arquetiposTier3.isNotEmpty()) {
                    item {
                        TierHeader(tierNumber = 3)
                    }
                    items(arquetiposTier3) { arquetipo ->
                        ArquetipoCard(
                            arquetipo = arquetipo
                        )
                    }
                }
            }
        }
    }
}
@Composable
private fun FondoSecundarioColor(): Color {
    return Color(0xFF1565C0).copy(alpha = 0.3f)
}