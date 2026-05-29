package org.example.Service

import model.Pokemon
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
                val opcion = consola.solicitarTipoRegistro()
                when (opcion) {
                    "1" -> {
                        val nombre = consola.solicitarNombrePokemon()
                        val tipos = consola.obtenerTipos()
                        val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                        repoSql.save(pokemon)
                    }

                    "2" -> {
                        val nombre = consola.solicitarNombrePokemon()
                        val tipos = consola.obtenerTipos()
                        val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                        repoSql.saveCaptura(pokemon)
                    }
                }

            } else if (opcion == "2") {
                val nombre = consola.solicitarNombrePokemon()
                val tipos = consola.obtenerTipos()
                val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                repoSql.update(pokemon)
            } else if (opcion == "3") {
                val id = consola.solicitarId()
                repoSql.delete(id)
            }else if (opcion == "4"){
                val opcion = consola.solicitarLista()
                when (opcion) {
                    "1" -> {
                        val listaPokemon = repoSql.listarPokemonRegistrados()
                        consola.mostrarTodos(listaPokemon)
                    }
                    "2" -> {
                        val nombre = consola.solicitarNombrePokemon()
                        val tipos = consola.obtenerTipos()
                        val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
                        repoSql.saveCaptura(pokemon)
                    }
                }

            }

        } while (opcion != "6")
    }
}