package com.example.digitalhub.data.repository

import com.example.digitalhub.data.local.FakeCartaDataSource
import com.example.digitalhub.domain.model.Carta
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Expansion
import com.example.digitalhub.domain.model.Nivel
import com.example.digitalhub.domain.model.RarezaCarta
import com.example.digitalhub.domain.model.TipoCarta
import com.example.digitalhub.domain.repository.BibliotecaRepository
import com.example.digitalhub.domain.repository.CartaRepository

class CartaRepositoryImpl(
    private val fakeDataSource : FakeCartaDataSource,
    private val bibliotecaRepository: BibliotecaRepository? = null
): CartaRepository {
    override suspend fun getCartas(): List<Carta> {
        return fakeDataSource.getCartas()
    }

    override suspend fun getCartaById(id: String): Carta? {
        return fakeDataSource.getCartas().find { it.id==id }
    }
    suspend fun getCartasConBiblioteca(userId: String): List<Carta> {
        if (bibliotecaRepository == null) {
            return getCartas()
        }

        val cartas = getCartas()
        val biblioteca = bibliotecaRepository.getCartasBiblioteca(userId)

        return cartas.map { carta ->
            val cartaBiblio = biblioteca.find { it.cartaId == carta.id }
            carta.copy(
                esFav = cartaBiblio?.esFavorita ?: false,
                cantidadEnBiblioteca = cartaBiblio?.cantidad ?: 0
            )
        }
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
        if (soloAlt){
            cartasFiltradas= cartasFiltradas.filter { it.esAlt }
        }

        return cartasFiltradas
    }
}