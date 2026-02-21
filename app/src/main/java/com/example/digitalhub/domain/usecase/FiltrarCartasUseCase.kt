package com.example.digitalhub.domain.usecase

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta
import com.example.digitalhub.domain.repository.CartaRepository

class FiltrarCartasUseCase(
    private val repository: CartaRepository
) {
    suspend operator fun invoke(
        color: ColorCarta? = null,
        coste: Int? = null,
        rareza: RarezaCarta? = null,
        tipo: TipoCarta? = null,
        nivel: Nivel? = null,
        expansion: Expansion? = null,
        soloFav: Boolean = false,
        soloAlt: Boolean = false
    ): List<Carta>
    {
        return repository.filtrarCartas(
            color = color,
            coste = coste,
            rareza = rareza,
            tipo = tipo,
            nivel = nivel,
            expansion = expansion,
            soloFav = soloFav,
            soloAlt = soloAlt
        )
    }
}