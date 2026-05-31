package repository.Dao

import model.Pokemon
import java.io.File

class DaoTxt {

    fun crear(){
        val archivo = File("./src/main/kotlin/data/txt/equipo.txt")

        archivo.parentFile.mkdirs()

        if (!archivo.exists()){
            archivo.createNewFile()
        }
    }

    fun guardar(listaPokemon: List<Pokemon>) {
        val archivo = File("./src/main/kotlin/data/txt/equipo.txt")

        archivo.parentFile.mkdirs()
        if (!archivo.exists()) archivo.createNewFile()

        val lineas = archivo.readLines().toMutableList()
        listaPokemon.forEach {
            lineas.add(it.toString())
        }
        archivo.writeText(lineas.joinToString("\n"))
    }

}