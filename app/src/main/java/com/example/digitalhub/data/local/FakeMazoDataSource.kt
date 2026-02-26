package com.example.digitalhub.data.local

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.R
import com.example.digitalhub.domain.model.ColorCarta

class FakeMazoDataSource {

    private val mazos = mutableListOf(
        Mazo(
            id = "1",
            nombre = "Red Aggro",
            colores = listOf(ColorCarta.RED),
            cartasNormales = 50,
            cartasHuevo = 4,
            portadaId = R.drawable.bt1025,
            tags = listOf("Aggro", "Tribal"),
            cartas = emptyList()
        ),
        Mazo(
            id = "2",
            nombre = "Omnimon Control",
            colores = listOf(ColorCarta.WHITE, ColorCarta.BLUE),
            cartasNormales = 50,
            cartasHuevo = 4,
            portadaId = R.drawable.bt1084,
            tags = listOf("Control"),
            cartas = emptyList()
        )
    )
    fun getMazos(): List<Mazo> {
        return mazos.toList()
    }

    fun getMazoById(id: String): Mazo? {
        return mazos.find { it.id == id }
    }

    fun crearMazo(mazo: Mazo) {
        mazos.add(mazo)
    }

    fun actualizarMazo(mazo: Mazo) {
        val index = mazos.indexOfFirst { it.id == mazo.id }
        if (index != -1) {
            mazos[index] = mazo
        }
    }

    fun eliminarMazo(id: String) {
        mazos.removeAll { it.id == id }
    }
}