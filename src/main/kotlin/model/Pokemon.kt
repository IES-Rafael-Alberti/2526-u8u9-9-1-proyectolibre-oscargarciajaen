package org.example.Modelo

data class Pokemon(
    val id: Int = 0,
    val nombre: String,
    val tipo1: Tipo,
    val tipo2: Tipo
) {

    override fun toString(): String {
        return "ID: $id | Nombre: $nombre | Tipo 1: $tipo1 | Tipo 2: $tipo2"
    }
}