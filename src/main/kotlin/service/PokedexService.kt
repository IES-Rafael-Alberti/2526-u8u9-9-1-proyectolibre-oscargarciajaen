package org.example.Service

import org.example.Modelo.Pokemon
import org.iesra.app.Console

object PokedexService {

    fun ejecutar(){
        val consola = Console
        val opcion = consola.solicitarOpcion()
        if (opcion == "1") {
            val nombre = consola.solicitarNombrePokemon()
            val tipos = consola.obtenerTipos()
            val pokemon: Pokemon = Pokemon(nombre, tipos.first, tipos.second)
            println(pokemon)
        } else
            println("coming soon")
    }
}