package org.example.Service

import model.Objeto
import model.Pokemon
import org.iesra.app.Console
import repository.Dao.DaoCsv
import repository.RepositorioMongo
import repository.RepositorioSQL
import repository.RepositorioTxt
import util.H2ConnectionManager

/**
 * Servicio principal de la Pokédex. Aquí se monta todo el flujo:
 * conexión con H2, conexión con Mongo y el menú que ve el usuario.
 * Es un `object` porque solo necesitamos una instancia viva.
 */
object PokedexService {

    /**
     * Arranca la app: prepara H2, los repositorios y muestra el menú
     * en bucle hasta que el usuario pulse 'X'.
     */
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

    /**
     * Pide al usuario el tipo de registro (solo Pokedex o también capturado)
     * y guarda el Pokémon en H2 según la opción.
     *
     * @param consola helper para leer datos del usuario.
     * @param repoSql repositorio donde se persiste el Pokémon.
     */
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

    /**
     * Pide un ID al usuario y libera (elimina) el Pokémon capturado con ese ID.
     *
     * @param consola helper para leer el ID.
     * @param repoSql repositorio donde se hace el delete.
     */
    private fun eliminar(consola: Console, repoSql: RepositorioSQL){
        val id = consola.solicitarId()
        repoSql.delete(id)
    }

    /**
     * Pide nombre y nuevos tipos al usuario y actualiza el Pokémon en la BD.
     *
     * @param consola helper para leer los datos.
     * @param repoSql repositorio donde se hace el update.
     */
    private fun actualizarPokemon(consola: Console, repoSql: RepositorioSQL){
        val nombre = consola.solicitarNombrePokemon()
        val tipos = consola.obtenerTipos()
        val pokemon = Pokemon(nombre = nombre, tipo1 = tipos.first, tipo2 = tipos.second)
        repoSql.update(pokemon)
    }

    /**
     * Pide qué lista quiere ver (registrados o capturados) y la muestra.
     *
     * @param consola helper para elegir lista y mostrarla.
     * @param repoSql repositorio del que se leen los Pokémon.
     */
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

    /**
     * Pide el nombre de un objeto y lo elimina del inventario en Mongo.
     *
     * @param consola helper para leer el nombre.
     * @param repoMongo repositorio de objetos en Mongo.
     */
    private fun eliminarObjeto(consola: Console, repoMongo: RepositorioMongo){
        val nombre = consola.solicitarNombreObjeto()
        repoMongo.delete(nombre)
    }

    /**
     * Registra un objeto nuevo: preguntando si viene por terminal o del CSV
     * y guardándolo en MongoDB.
     *
     * @param consola helper para pedir origen, nombre y cantidad.
     * @param repoMongo repositorio donde se persisten los objetos.
     */
    private fun ingresarObjeto(consola:Console, repoMongo: RepositorioMongo){
        val origen = consola.solicitarOrigenObjeto()
        when (origen) {
            "1" -> {
                val nombre = consola.solicitarNombreObjeto()
                val cantidad = consola.solicitarCantidad()
                val objeto = Objeto(nombre, cantidad)
                repoMongo.save(objeto)
            }
            "2" -> {
                val daoCsv = DaoCsv()
                val objetos = daoCsv.leerObjetos()
                if (objetos.isEmpty()) {
                    println("El CSV no contenía objetos válidos.")
                } else {
                    objetos.forEach { repoMongo.save(it) }
                }
            }
        }
    }

    /**
     * Pide nombre y cantidad nueva y actualiza el objeto en Mongo.
     *
     * @param consola helper para leer los datos.
     * @param repoMongo repositorio donde se hace el update.
     */
    private fun actualizarObjeto(consola: Console, repoMongo: RepositorioMongo){
        val nombre = consola.solicitarNombreObjeto()
        val cantidad = consola.solicitarCantidad()
        repoMongo.update(nombre, cantidad)
    }

    /**
     * Recupera todos los objetos de MongoDB y los muestra por consola.
     *
     * @param consola helper para imprimirlos.
     * @param repoMongo repositorio de objetos en Mongo.
     */
    private fun listarObjetos(consola: Console, repoMongo: RepositorioMongo){
        val lista = repoMongo.mostrarObjetos()
        consola.mostrarTodos(lista)
    }

    /**
     * Genera un equipo aleatorio de hasta 6 Pokémon capturados y lo guarda
     * en un fichero de texto plano.
     *
     * Si hay 6 o menos capturados, se cogen todos. Si hay más, se eligen 6 al azar.
     *
     * @param repoSql repositorio del que se leen los capturados.
     */
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