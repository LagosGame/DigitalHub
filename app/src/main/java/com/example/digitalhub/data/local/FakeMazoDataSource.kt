package com.example.digitalhub.data.local

import com.example.digitalhub.domain.model.Mazo
import com.example.digitalhub.R
import com.example.digitalhub.domain.model.CartaEnMazo
import com.example.digitalhub.domain.model.ColorCarta
import com.example.digitalhub.domain.model.Estadisticas
import com.example.digitalhub.domain.model.Estrategia

class FakeMazoDataSource {

    private val mazos = mutableListOf(
        Mazo(
            id = "1",
            nombre = "Red Aggro",
            colores = listOf(ColorCarta.RED),
            cartasNormales = 50,
            cartasHuevo = 5,
            portadaId = R.drawable.bt1025,  // WarGreymon
            tags = listOf("Aggro", "Tribal", "Meta"),
            cartas = listOf(

                CartaEnMazo("BT1-001", 3),
                CartaEnMazo("BT1-002", 2),


                CartaEnMazo("BT1-009", 3),
                CartaEnMazo("BT1-010", 4),
                CartaEnMazo("BT1-011", 1),
                CartaEnMazo("BT1-013", 4),


                CartaEnMazo("BT1-015", 4),
                CartaEnMazo("BT1-016", 2),
                CartaEnMazo("BT1-017", 4),
                CartaEnMazo("BT1-019", 4),
                CartaEnMazo("ST1-06", 2),


                CartaEnMazo("BT1-020", 3),
                CartaEnMazo("BT1-021", 2),
                CartaEnMazo("BT1-114", 3),


                CartaEnMazo("BT1-025", 2),
                CartaEnMazo("BT1-026", 4),


                CartaEnMazo("BT1-085", 4),


                CartaEnMazo("BT1-095", 4),
            ),
            descripcion = "Mazo agresivo que se centra en ganar rápido evolucionando mucho y presionando al oponente con Digimon de nivel alto.",
            estrategias = listOf(
                Estrategia(
                    titulo = "Evolución de mayor daño",
                    cartasIds = listOf(
                        "BT1-010",
                        "BT1-015",
                        "BT1-021",
                        "BT1-025"
                    )
                ),
                Estrategia(
                    titulo = "Línea alternativa",
                    cartasIds = listOf("BT1-009", "BT1-019", "BT1-020", "BT1-026")  // Monodramon → DarkTyrannomon → Groundramon → Breakdramon
                )
            ),
            cartasImportantes = listOf(
                "BT1-085",
                "BT1-025",
                "BT1-095"
            ),
            estadisticas = Estadisticas(
                ataque = 9,
                defensa = 5,
                consistencia = 6,
                versatilidad = 4,
                recuperacion = 5
            )
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