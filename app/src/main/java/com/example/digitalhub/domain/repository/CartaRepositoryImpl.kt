package com.example.digitalhub.domain.repository

import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta

class CartaRepositoryImpl(
    private val fakeDataSource : FakeCartaDataSource
): CartaRepository {
    override suspend fun getCartas(): List<Carta> {
        return fakeDataSource.getCartas()
    }

    override suspend fun getCartaById(id: String): Carta? {
        return fakeDataSource.getCartas().find { it.id==id }
    }

    override suspend fun filtrarCartas(
        color: ColorCarta?,
        coste: Int?,
        rareza: RarezaCarta?,
        tipo: TipoCarta?,
        nivel: Nivel?,
        expansion: Expansion?,
        soloFav: Boolean,
        soloAlt: Boolean,
        soloMiBiblioteca: Boolean
    ): List<Carta> {

        var cartasFiltradas = fakeDataSource.getCartas()

        color?.let {
            cartasFiltradas= cartasFiltradas.filter { carta ->
                carta.color.contains(it)
            }
        }
        coste?.let {
            cartasFiltradas=cartasFiltradas.filter { it.coste==coste }
        }
        rareza?.let {
            cartasFiltradas=cartasFiltradas.filter { it.rareza==rareza }
        }
        tipo?.let {
            cartasFiltradas=cartasFiltradas.filter { it.tipo==tipo }
        }
        nivel?.let {
            cartasFiltradas=cartasFiltradas.filter { it.nivel==nivel }
        }
        expansion?.let {
            cartasFiltradas=cartasFiltradas.filter { it.expansion==expansion }
        }
        if (soloFav){
            cartasFiltradas= cartasFiltradas.filter { it.esFav }
        }
        if (soloAlt){
            cartasFiltradas= cartasFiltradas.filter { it.esAlt }
        }
        if (soloMiBiblioteca) {
            cartasFiltradas = cartasFiltradas.filter { it.cantidadEnBiblioteca > 0 }
        }

        return cartasFiltradas
    }
}