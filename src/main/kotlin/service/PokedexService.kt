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
        var opcion: String? = null


        do {
            opcion = consola.solicitarOpcion()
            if (opcion == "1") {
                val nombre = consola.solicitarNombrePokemon()
                val tipos = consola.obtenerTipos()
                val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                repoSql.save(pokemon)
            } else if (opcion == "2") {
                val nombre = consola.solicitarNombrePokemon()
                val tipos = consola.obtenerTipos()
                val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                repoSql.update(pokemon)
            } else if (opcion == "3") {
                val id = consola.solicitarId()
                repoSql.delete(id)
            }else if (opcion == "4"){
                val listaPokemon = repoSql.listarPokemon()
                consola.mostrarTodos(listaPokemon)
            }

        } while (opcion != "6")
    }
}