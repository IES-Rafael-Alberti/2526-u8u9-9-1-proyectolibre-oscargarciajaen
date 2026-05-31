package org.example.Service

import model.Objeto
import model.Pokemon
import org.iesra.app.Console
import repository.RepositorioMongo
import repository.RepositorioSQL
import repository.RepositorioTxt
import util.H2ConnectionManager
import util.MongoConecctionManager

object PokedexService {

    fun ejecutar(){
        val h2 = H2ConnectionManager()
        val conexion = h2.create()
        val mongo = MongoConecctionManager()
        val conextionMongo = mongo.obtenerMongoDB()
        h2.createTables(conexion)
        val repoSql = RepositorioSQL(conexion)
        val repoMongo = RepositorioMongo()
        val consola = Console
        var opcion: String? = null

        do {
            opcion = consola.solicitarOpcion()
            if (opcion == "1") {
                registrar(consola, repoSql)
            } else if (opcion == "2") {
                actualizarPokemon(consola, repoSql)
            } else if (opcion == "3") {
                val id = consola.solicitarId()
                repoSql.delete(id)
            }else if (opcion == "4"){
                obtenerLista(consola, repoSql)
            } else if (opcion == "5") {
                val nombre = consola.solicitarNombreObjeto()
                val cantidad = consola.solicitarCantidad()
                val objeto = Objeto(nombre, cantidad)
                repoMongo.save(objeto)
            } else if (opcion == "9") {
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
}