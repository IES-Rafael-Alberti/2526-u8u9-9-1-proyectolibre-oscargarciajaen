package model

data class Objeto(val nombre: String, val cantidad: Int, ) {
    override fun toString(): String {
         return "Nombre: $nombre | Cantidad: $cantidad"
    }
}