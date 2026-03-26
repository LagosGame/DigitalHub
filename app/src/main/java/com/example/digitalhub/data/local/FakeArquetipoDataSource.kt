package com.example.digitalhub.data.local

import com.example.digitalhub.domain.model.Arquetipo
import com.example.digitalhub.domain.model.Tier
import com.example.digitalhub.R
import com.example.digitalhub.domain.model.ColorCarta

class FakeArquetipoDataSource {
    private val arquetipos = listOf(
        Arquetipo(
            id = "arq1",
            nombre = "Omega Red & Blue",
            tier = Tier.TIER_1,
            color = ColorCarta.RED,
            imagenId = R.drawable.ic_launcher_foreground,
            descripcion = "Arquetipo dominante del meta"
        ),

        Arquetipo(
            id = "arq2",
            nombre = "UlforceVdramon Rush",
            tier = Tier.TIER_2,
            color = ColorCarta.BLUE,
            imagenId = R.drawable.ic_launcher_foreground,
        ),
        Arquetipo(
            id = "arq4",
            nombre = "Milleniummon Purple",
            tier = Tier.TIER_2,
            color = ColorCarta.PURPLE,
            imagenId = R.drawable.ic_launcher_foreground,
        ),
        Arquetipo(
            id = "arq3",
            nombre = "ShineGreymon SG",
            tier = Tier.TIER_3,
            color = ColorCarta.YELLOW,
            imagenId = R.drawable.ic_launcher_foreground,
            descripcion = "Control de tablero"
        )
    )

    fun getArquetipos(): List<Arquetipo> = arquetipos

    fun getArquetiposPorTier(tier: Tier): List<Arquetipo> {
        return arquetipos.filter { it.tier == tier }
    }
}