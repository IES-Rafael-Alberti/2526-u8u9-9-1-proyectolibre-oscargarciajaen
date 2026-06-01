package model

/**
 * Representa un objeto del inventario (pokéballs, pociones, etc.).
 * Guarda el nombre y cuántas unidades tienes.
 *
 * @property nombre nombre del objeto.
 * @property cantidad cuántas unidades hay en el inventario.
 */
data class Objeto(val nombre: String, val cantidad: Int, ) {
    override fun toString(): String {
         return "Nombre: $nombre | Cantidad: $cantidad"
    }
}