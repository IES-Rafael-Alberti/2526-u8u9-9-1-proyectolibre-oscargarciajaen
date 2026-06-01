package org.example.Service

import model.Objeto
import model.Pokemon
import org.iesra.app.Console
import repository.RepositorioMongo
import repository.RepositorioSQL
import repository.RepositorioTxt
import util.H2ConnectionManager

object PokedexService {

    fun ejecutar(){
        val h2 = H2ConnectionManager()
        val conexion = h2.create()
        h2.createTables(conexion)
        val repoSql = RepositorioSQL(conexion)
        val repoMongo = RepositorioMongo()
        val consola = Console
        var opcion: String? = null

        do {
            opcion = consola.solicitarOpcion()
            when (opcion) {
                "1" -> { registrar(consola, repoSql) }
                "2" -> { actualizarPokemon(consola, repoSql) }
                "3" -> { eliminar(consola, repoSql) }
                "4" -> { obtenerLista(consola, repoSql) }
                "5" -> { ingresarObjeto(consola, repoMongo) }
                "6" -> { eliminarObjeto(consola, repoMongo) }
                "7" -> { actualizarObjeto(consola, repoMongo) }
                "8" -> { listarObjetos(consola, repoMongo) }
                "9" -> { crearEquipo(repoSql) }
            }
        } while (opcion != "x" && opcion != "X")
    }

    private fun registrar(consola: Console, repoSql: RepositorioSQL){
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
    }

    private fun eliminar(consola: Console, repoSql: RepositorioSQL){
        val id = consola.solicitarId()
        repoSql.delete(id)
    }

    private fun actualizarPokemon(consola: Console, repoSql: RepositorioSQL){
        val nombre = consola.solicitarNombrePokemon()
        val tipos = consola.obtenerTipos()
        val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
        repoSql.update(pokemon)
    }

    private fun obtenerLista(consola: Console, repoSql: RepositorioSQL){
        val opcion = consola.solicitarLista()
        when (opcion) {
            "1" -> {
                val listaPokemon = repoSql.listarPokemonRegistrados()
                consola.mostrarTodos(listaPokemon)
            }
            "2" -> {
                val listaPokemon = repoSql.listarPokemonCapturados()
                consola.mostrarTodos(listaPokemon)
            }
        }
    }

    private fun eliminarObjeto(consola: Console, repoMongo: RepositorioMongo){
        val nombre = consola.solicitarNombreObjeto()
        repoMongo.delete(nombre)
    }

    private fun ingresarObjeto(consola:Console, repoMongo: RepositorioMongo){
        val nombre = consola.solicitarNombreObjeto()
        val cantidad = consola.solicitarCantidad()
        val objeto = Objeto(nombre, cantidad)
        repoMongo.save(objeto)
    }

    private fun actualizarObjeto(consola: Console, repoMongo: RepositorioMongo){
        val nombre = consola.solicitarNombreObjeto()
        val cantidad = consola.solicitarCantidad()
        repoMongo.update(nombre, cantidad)
    }

    private fun listarObjetos(consola: Console, repoMongo: RepositorioMongo){
        val lista = repoMongo.mostrarObjetos()
        consola.mostrarTodos(lista)
    }

    private fun crearEquipo(repoSql: RepositorioSQL){
        val listaPokemon = repoSql.listarPokemonCapturados()
        val listaEquipo = mutableListOf<Pokemon>()

        if (listaPokemon.size <= 6) {
            listaPokemon.forEach {
                listaEquipo.add(it)
            }
        } else {
            val indicesAleatorios = mutableListOf<Int>()

            while (indicesAleatorios.size < 6) {
                val numeroAzar = (0 until listaPokemon.size).random()

                if (numeroAzar !in indicesAleatorios) {
                    indicesAleatorios.add(numeroAzar)
                }
            }

            indicesAleatorios.forEach { it ->
                val pokemonAleatorio = listaPokemon[it]
                listaEquipo.add(pokemonAleatorio)
            }
        }
        val repoTxt = RepositorioTxt()
        repoTxt.crear()
        repoTxt.guardar(listaEquipo)
    }
}