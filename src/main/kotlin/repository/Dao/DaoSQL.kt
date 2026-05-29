package repository.Dao

import org.example.Modelo.Pokemon
import org.example.Modelo.Tipo
import java.sql.Connection
import java.sql.SQLException

class DaoSQL {

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

    fun delete(conexion: Connection?, id: Int) {
        val sql = "DELETE FROM POKEMON WHERE ID = ?"

        if (conexion != null) {
            try {
                conexion.prepareStatement(sql).use { statement ->
                    statement?.setInt(1, id)

                    val filasEliminadas = statement?.executeUpdate() ?: 0
                    println("Número de pokémons eliminados: $filasEliminadas")
                }
            } catch (sqlException: SQLException) {
                println("Error en delete1: ${sqlException.message}")
            }
        }
    }

    fun listarTodos(conexion: Connection?): List<Pokemon>{

        var listaPokemon = mutableListOf<Pokemon>()

        if (conexion != null) {

            val sql = "SELECT * FROM POKEMON"

            try {
                conexion.prepareStatement(sql).use { statement ->

                    val resultSet = statement?.executeQuery()

                    while (resultSet?.next() == true) {

                        val id = resultSet.getInt("id")
                        val nombre = resultSet.getString("nombre")

                        val tipo1String = resultSet.getString("tipo1")
                        val tipo2String = resultSet.getString("tipo2")

                        val tipo1 = Tipo.valueOf(tipo1String)
                        val tipo2 = Tipo.valueOf(tipo2String)


                        val pokemon = Pokemon(id, nombre, tipo1, tipo2)

                        listaPokemon.add(pokemon)
                    }
                }
            } catch (e: SQLException) {
                println("Error al listar: ${e.message}")
            }
        }
        return listaPokemon
    }

    fun update(conexion: Connection?, entity: Pokemon){
        val sql = "UPDATE POKEMON SET tipo1 = ?, tipo2 = ? WHERE nombre = ?"

        if (conexion != null) {
            try {
                conexion.prepareStatement(sql).use { statement ->

                    statement.setString(1, entity.tipo1.toString())
                    statement.setString(2, entity.tipo2.toString())
                    statement.setString(3, entity.nombre)

                    val filasActualizadas = statement.executeUpdate()
                    println("Numero de filas actualizadas: $filasActualizadas")

                }
            } catch (e: SQLException) {
                println("Error al actualizar el Pokémon: ${e.message}")
            }
        }

    }
}