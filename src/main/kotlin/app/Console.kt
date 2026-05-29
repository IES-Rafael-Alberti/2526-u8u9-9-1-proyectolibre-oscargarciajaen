package org.iesra.app

import org.example.Modelo.Pokemon
import org.example.Modelo.Tipo

object Console {

    fun solicitarOpcion(): String{

        val opciones = listOf("1", "2", "3", "4", "5", "6")
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

    fun mostrarTodos(listaPokemon: List<Pokemon>){
        listaPokemon.forEach {
            println(it)
        }
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
        println("3. Eliminar Pokémon")
        println("4. Listar Pokémon")
        println("5. Generar equipo Pokémon aleatorio")
        println("6. Salir")
        println()
        print("Tu opcion -> ")
    }
}