package com.example.digitalhub.domain.repository

import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta

interface CartaRepository {

    suspend fun getCartas(): List<Carta>

    suspend fun getCartaById(id: String): Carta?

    suspend fun filtrarCartas(
        color: ColorCarta? = null,
        coste : Int? = null,
        rareza : RarezaCarta? = null,
        tipo : TipoCarta? = null,
        nivel : Nivel? = null,
        expansion : Expansion? = null,
        soloFav : Boolean = false,
        soloAlt : Boolean = false,
        soloMiBiblioteca: Boolean = false
    ): List<Carta>


}