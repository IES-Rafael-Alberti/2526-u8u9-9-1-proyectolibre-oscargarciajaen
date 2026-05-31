package model

import org.example.Modelo.Tipo

data class Objeto(
    val nombre: String,
    val cantidad: Int,
    val id: Int? = null
) {

    constructor(id: Int, nombre: String, cantidad: Int) : this(nombre, cantidad,)

    override fun toString(): String {
        return if (id != null) {
            "ID: $id | Nombre: $nombre | Cantidad: $cantidad"
        } else {
            "Nombre: $nombre | Cantidad: $cantidad"
        }
    }
}