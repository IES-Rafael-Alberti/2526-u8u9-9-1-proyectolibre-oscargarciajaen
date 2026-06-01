package model

/**
 * Modelo principal de un Pokémon en la app.
 * Puede tener uno o dos tipos, y el id es opcional porque
 * los recién registrados todavía no lo tienen.
 *
 * @property nombre nombre del Pokémon.
 * @property tipo1 tipo principal.
 * @property tipo2 tipo secundario (o [Tipo.False] si solo tiene uno).
 * @property id identificador en BD, opcional hasta que se persiste.
 */
data class Pokemon(
    val nombre: String,
    val tipo1: Tipo,
    val tipo2: Tipo,
    val id: Int? = null
) {

    constructor(id: Int, nombre: String, tipo1: Tipo, tipo2: Tipo) : this(nombre, tipo1, tipo2, id)

    override fun toString(): String {
        return if (id != null) {
            "ID: $id | Nombre: $nombre | Tipo 1: $tipo1 | Tipo 2: $tipo2"
        } else {
            "Nombre: $nombre | Tipo 1: $tipo1 | Tipo 2: $tipo2"
        }
    }
}