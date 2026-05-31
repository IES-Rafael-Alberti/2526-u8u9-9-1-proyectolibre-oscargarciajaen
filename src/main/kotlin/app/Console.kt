package org.iesra.app

import model.Pokemon
import org.example.Modelo.Tipo

object Console {

    fun solicitarOpcion(): String{

        val opciones = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "x")
        var opcion: String? = null
        do {
            mostrarMenu()
            val entrada = readLine()
            if (entrada in opciones) {
                opcion = entrada
            } else
                println("Opción no válida.\n")
        } while (opcion == null)
        return opcion
    }

    fun solicitarNombrePokemon(): String {

        var nombre: String? = null
        do {
            println("Introduce el nombre del pokemon: ")
            val entrada = readLine()?.trim()
            if (!entrada.isNullOrBlank()) {
                nombre = entrada
            }
        } while (nombre == null)
        return nombre
    }

    fun obtenerTipos(): Pair<Tipo, Tipo> {
        var tipo1: Tipo = Tipo.False

        do {
            tipo1 = solicitarTipo()
            if (tipo1 == Tipo.False)
                println("El tipo 1 no es válido")
        } while (tipo1 == Tipo.False)

        val tipo2: Tipo = solicitarTipo(tipo1)
        val tipos: Pair<Tipo, Tipo> = tipo1 to tipo2
        return tipos
    }

    fun solicitarLista(): String {
        var opcion: String? = null
        do {
            println("1. Listar Pokémon registrados")
            println("2. Listar Pokémon capturados")
            println()
            print("Introduzca su opción -> ")
            val entrada = readLine()
            if (entrada == "1" || entrada == "2") {
                opcion = entrada
            } else
                println("Opción no válida.\n")
        } while (opcion == null)
        return opcion
    }

    fun solicitarId(): Int {
        var id: Int? = null

        do {
            println("Introduce el ID del Pokémon que quieres eliminar")
            val entrada = readLine()?.trim()
            if (entrada != null && entrada.all { it.isDigit() }) {
                id = entrada.toInt()
            }
        } while (id == null)
        return id
    }

    fun <T> mostrarTodos(lista: List<T>){
        lista.forEach {
            println(it)
        }
        println()
    }

    fun solicitarTipoRegistro(): String {
        var opcion: String? = null
        do {
            println("1. Registrar en la Pokedex")
            println("2. Registrar en la Pokedex y Capturar")
            println()
            print("Introduzca su opción -> ")
            val entrada = readLine()
            if (entrada == "1" || entrada == "2") {
                opcion = entrada
            } else
                println("Opción no válida.\n")
        } while (opcion == null)
        return opcion
    }

    fun solicitarNombreObjeto(): String {

        var nombre: String? = null
        do {
            println("Introduce el nombre del objeto: ")
            val entrada = readLine()?.trim()
            if (!entrada.isNullOrBlank()) {
                nombre = entrada
            }
        } while (nombre == null)
        return nombre
    }

    fun solicitarCantidad(): Int {
        var cantidad: Int? = null
        do {
            println("Introduce el nombre del objeto: ")
            val entrada = readLine()?.trim()
            if (entrada != null && entrada.all { it.isDigit() }) {
                val entradaInt = entrada.toInt()
                if (entradaInt > 0 && entradaInt < 100) {
                    cantidad = entradaInt
                }
            }
        } while (cantidad == null)
        return cantidad
    }

    private fun mostrarMenu(){
        println("###################")
        println("#     Pokedex     #")
        println("###################")
        println()
        println("Elige una opción: ")
        println()
        println("1. Registrar un Pokémon")
        println("2. Actualizar tipos de un Pokémon")
        println("3. Liberar Pokémon")
        println("4. Listar Pokémon")
        println("5. Registrar Objeto")
        println("6. Tirar Objeto")
        println("7. Actualizar cantidad de x Objeto")
        println("8. Listar Objeto")
        println("9. Generar equipo Pokémon aleatorio")
        println()
        println("Pulsa 'X' para Salir")
        println()
        print("Tu opcion -> ")
    }

    private fun solicitarTipo(tipo1: Tipo): Tipo {
        val listaTipos = Tipo.values()
        var tipo2: Tipo? = null

        do {
            println("Introduce el segundo tipo del Pokémon en formato 'Acero' (no repitas tipo) o 'False' en caso de que solo tenga un tipo:")
            val entrada = readLine()?.trim()
            listaTipos.forEach {
                if (entrada == it.toString() && entrada != tipo1.toString()){
                    tipo2 = it
                }
            }
        } while (tipo2 == null)
        return tipo2
    }

    private fun solicitarTipo(): Tipo {
        val listaTipos = Tipo.values()
        println("Introduce el primer tipo del Pokémon en formato 'Acero' :")
        val entrada = readLine()?.trim()
        listaTipos.forEach {
            if (entrada == it.toString()){
                return it
            }
        }
        return Tipo.False
    }
}