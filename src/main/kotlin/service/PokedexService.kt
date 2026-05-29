package org.example.Service

import org.example.Modelo.Pokemon
import org.iesra.app.Console
import repository.RepositorioSQL
import util.H2ConnectionManager

object PokedexService {

    fun ejecutar(){
        val h2 = H2ConnectionManager()
        val conexion = h2.create()
        h2.createTables(conexion)
        val repoSql = RepositorioSQL(conexion)
        val consola = Console
        val opcion = consola.solicitarOpcion()
        if (opcion == "1") {
            val nombre = consola.solicitarNombrePokemon()
            val tipos = consola.obtenerTipos()
            val pokemon: Pokemon = Pokemon(nombre, tipos.first, tipos.second)
            repoSql.save(pokemon)
        } else if (opcion == "2") {
            val nombre = consola.solicitarNombrePokemon()
            val tipos = consola.obtenerTipos()
            val pokemon: Pokemon = Pokemon(nombre, tipos.first, tipos.second)
            repoSql.update(pokemon)
        } else
            println("coming soon")
    }
}