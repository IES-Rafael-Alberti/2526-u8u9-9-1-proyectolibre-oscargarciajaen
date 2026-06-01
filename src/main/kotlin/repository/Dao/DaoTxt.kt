package repository.Dao

import model.Objeto
import model.Pokemon
import java.io.File

/**
 * DAO que trabaja con un fichero de texto para guardar el equipo de Pokemon.
 * Básicamente lee y escribe en equipo.txt, nada del otro mundo.
 */
class DaoTxt : Dao<Pokemon, String>() {

    /**
     * Crea el fichero equipo.txt si no existe.
     * Primero se asegura de que la carpeta exista y luego crea el archivo.
     */
    fun crear(){
        val archivo = File("./src/main/kotlin/data/txt/equipo.txt")

        archivo.parentFile.mkdirs()

        try {
            if (!archivo.exists()){
                archivo.createNewFile()
            }
        } catch (e: Exception) {
            println("Error al crear el archivo: ${e.message}")
        }
    }

    /**
     * Guarda la lista de Pokemon en el fichero, añadirlos al final
     * de lo que ya haya escrito, sin pisar lo anterior.
     *
     * @param listaPokemon los Pokemon que quieres guardar en el archivo.
     */
    fun guardar(listaPokemon: List<Pokemon>) {
        val archivo = File("./src/main/kotlin/data/txt/equipo.txt")

        archivo.parentFile.mkdirs()
        if (!archivo.exists()) archivo.createNewFile()

        try {
            val lineas = archivo.readLines().toMutableList()
            listaPokemon.forEach {
                lineas.add(it.toString())
            }
            archivo.writeText(lineas.joinToString("\n"))
        } catch (e: Exception) {
            println("Error al guardar en el archivo: ${e.message}")
        }
    }
}