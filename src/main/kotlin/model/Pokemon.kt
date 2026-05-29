package model

import org.example.Modelo.Tipo

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