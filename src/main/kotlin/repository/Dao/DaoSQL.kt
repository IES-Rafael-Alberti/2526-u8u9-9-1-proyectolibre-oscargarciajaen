package repository.Dao

import org.example.Modelo.Pokemon
import java.sql.Connection
import java.sql.SQLException

class DaoSQL() {

    fun save(conexion: Connection?, entity: Pokemon) {
        val sql = "INSERT INTO POKEMON (nombre, tipo1, tipo2) VALUES (?, ?, ?)"

        if (conexion != null) {
            try {
                conexion.prepareStatement(sql).use { statement ->

                    statement.setString(1, entity.nombre)
                    statement.setString(2, entity.tipo1.toString())
                    statement.setString(3, entity.tipo2.toString())

                    statement.executeUpdate()
                }
            } catch (e: SQLException) {
                println("Error al guardar el Pokémon: ${e.message}")
            }
        }
    }

    fun delete(id: Int){

    }

    /*fun listarTodos(): List<Pokemon>{

    }*/

    fun update(conexion: Connection?, entity: Pokemon){
        val sql = "UPDATE POKEMON SET tipo1 = ?, tipo2 = ? WHERE nombre = ?"

        if (conexion != null) {
            try {
                conexion.prepareStatement(sql).use { statement ->

                    statement.setString(1, entity.tipo1.toString())
                    statement.setString(2, entity.tipo2.toString())
                    statement.setString(3, entity.nombre)

                    val filasActualizadas = statement.executeUpdate()

                }
            } catch (e: SQLException) {
                println("Error al actualizar el Pokémon: ${e.message}")
            }
        }

    }
}