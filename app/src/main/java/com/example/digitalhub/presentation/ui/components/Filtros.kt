package com.example.digitalhub.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digitalhub.domain.model.*
import com.example.digitalhub.presentation.ui.state.BibliotecaUiState
import com.example.digitalhub.presentation.ui.state.Selector

@Composable
fun Filtros(
    uiState: BibliotecaUiState,
    onAbrirSelector: (Selector) -> Unit,
    onSeleccionarColor: (ColorCarta) -> Unit,
    onSeleccionarCoste: (Int) -> Unit,
    onSeleccionarRareza: (RarezaCarta) -> Unit,
    onSeleccionarTipo: (TipoCarta) -> Unit,
    onSeleccionarNivel: (Nivel) -> Unit,
    onSeleccionarExpansion: (Expansion) -> Unit,
    onActivarFavoritas: () -> Unit,
    onActivarAlternativas: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //1ª
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //Color
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonColor(
                    text = uiState.colorFiltro?.nombreDisplay?.first()?.toString() ?: "A",
                    color = uiState.colorFiltro?.toColor() ?: Color.Gray,
                    onClick = { onAbrirSelector(Selector.COLOR) }
                )
                if (uiState.selectorAbierto == Selector.COLOR) {
                    SelectorColor { onSeleccionarColor(it) }
                }
            }

            //Coste
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonColor(
                    text = uiState.costeFiltro?.toString() ?: "0",
                    color = Color.Gray,
                    onClick = { onAbrirSelector(Selector.COSTE) }
                )
                if (uiState.selectorAbierto == Selector.COSTE) {
                    SelectorCostes { onSeleccionarCoste(it) }
                }
            }

            //Rareza
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonColor(
                    text = uiState.rarezaFiltro?.letra ?: "A",
                    color = Color.Gray,
                    onClick = { onAbrirSelector(Selector.RAREZA) }
                )
                if (uiState.selectorAbierto == Selector.RAREZA) {
                    SelectorRarezas { onSeleccionarRareza(it) }
                }
            }

            //Nivel
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonFiltro(
                    texto = uiState.nivelFiltro?.etiqueta ?: "Lv",
                    onClick = { onAbrirSelector(Selector.NIVEL) }
                )
                if (uiState.selectorAbierto == Selector.NIVEL) {
                    SelectorNivel { onSeleccionarNivel(it) }
                }
            }

            //Tipo
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonFiltro(
                    texto = uiState.tipoFiltro?.nombreDisplay ?: "ALL",
                    onClick = { onAbrirSelector(Selector.TIPO) }
                )
                if (uiState.selectorAbierto == Selector.TIPO) {
                    SelectorTipo { onSeleccionarTipo(it) }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //2ª
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //Expansión
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BotonFiltro(
                    texto = uiState.expansionFiltro?.codigo ?: "ALL",
                    onClick = { onAbrirSelector(Selector.EXPANSION) }
                )
                if (uiState.selectorAbierto == Selector.EXPANSION) {
                    SelectorExpansion { onSeleccionarExpansion(it) }
                }
            }

            //Favoritas
            BotonFiltroOn(
                texto = "Favoritas",
                seleccionado = uiState.soloFav,
                onClick = onActivarFavoritas
            )

            //Alternativas
            BotonFiltroOn(
                texto = "Alternativas",
                seleccionado = uiState.soloAlt,
                onClick = onActivarAlternativas
            )
        }
    }
}