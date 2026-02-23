package com.example.digitalhub.presentation.ui.state

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta


data class BibliotecaUiState(
    val cartas : List<Carta> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage : String? = null,

    //filtros//

    val colorFiltro: ColorCarta? = null,
    val costeFiltro: Int? = null,
    val rarezaFiltro: RarezaCarta? = null,
    val tipoFiltro: TipoCarta? = null,
    val nivelFiltro: Nivel? = null,
    val expansionFiltro: Expansion? = null,
    val soloFav: Boolean = false,
    val soloAlt: Boolean = false,


    //uistate//

    val selectorAbierto : Selector? = null,
    val busqueda : String = ""
    )
enum class Selector{
    COLOR,
    COSTE,
    RAREZA,
    TIPO,
    NIVEL,
    EXPANSION
}