package com.example.digitalhub.domain.model

data class Carta(
    val id : String,
    val nombre : String,
    val imagenId: Int,
    val color : List<ColorCarta>,
    val coste : Int,
    val costeEvolucion: Int? = null,
    val rareza : RarezaCarta,
    val tipo : TipoCarta,
    val nivel : Nivel?,
    val expansion : Expansion,

    //Parte usuario//
    val esFav : Boolean = false,
    val cantidadEnBiblioteca: Int = 0,

    //Alternativa
    val esAlt : Boolean = false,

    //Info detallada
    val dp: Int? = null,
    val atributo: String? = null,
    val texto: String? = null
)

enum class ColorCarta(val nombreDisplay : String){
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLACK("Black"),
    PURPLE("Purple"),
    WHITE("White"),
    RAINBOW("Rainbow")
}
enum class RarezaCarta(val letra : String,
    val nombreDisplay : String){
    COMMON("C", "Common"),
    UNCOMMON("U", "Uncommon"),
    RARE("R", "Rare"),
    SUPER_RARE("SR", "Super Rare"),
    SECRET_RARE("SEC", "Secret Rare"),
}
enum class TipoCarta(val nombreDisplay : String)
{
    DIGIMON("Digimon"),
    TAMER("Tamer"),
    OPTION("Option"),
    DIGIEGG("Digi-Egg")
}
enum class Nivel(val etiqueta: String, val value: Int)
{
    LV2("Lv.2", 2),
    LV3("Lv.3", 3),
    LV4("Lv.4", 4),
    LV5("Lv.5", 5),
    LV6("Lv.6", 6),
    LV7("Lv.7", 7)
}

data class Expansion(
    val codigo : String,
    val nombre: String
)
val expansiones = listOf(
    Expansion("BT-01","New Evolution"),
    Expansion("BT-02", "Ultimate Power"),
    Expansion("BT-03", "Union Impact"),
    Expansion("ST-01", "Gaia Red"),
    Expansion("ST-02", "Cocytus Blue"),
    Expansion("ST-03", "Heaven's Yellow")
)


