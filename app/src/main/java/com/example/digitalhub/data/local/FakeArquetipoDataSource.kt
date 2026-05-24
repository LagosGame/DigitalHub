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
            imagenId = "BT1-084",
            descripcion = "Arquetipo dominante del meta"
        ),

        Arquetipo(
            id = "arq2",
            nombre = "UlforceVdramon Rush",
            tier = Tier.TIER_2,
            color = ColorCarta.BLUE,
            imagenId = "BT2-032",
        ),
        Arquetipo(
            id = "arq4",
            nombre = "Milleniummon Purple",
            tier = Tier.TIER_2,
            color = ColorCarta.PURPLE,
            imagenId = "BT2-083",
        ),
        Arquetipo(
            id = "arq3",
            nombre = "ShineGreymon SG",
            tier = Tier.TIER_3,
            color = ColorCarta.YELLOW,
            imagenId = "BT2-041"
        )
    )

    fun getArquetipos(): List<Arquetipo> = arquetipos
}