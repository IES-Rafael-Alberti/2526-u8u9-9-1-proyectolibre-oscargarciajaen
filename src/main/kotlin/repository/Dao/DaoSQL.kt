package repository.Dao

import model.Pokemon
import model.Tipo
import java.sql.Connection
import java.sql.SQLException
import kotlin.use

/**
 * DAO que hace las queries SQL contra la base de datos H2.
 * Aquí está todo el CRUD de Pokémon a base de prepared statements.
 */
class DaoSQL : Dao<Pokemon, Int>() {

    /**
     * Inserta un Pokémon en la tabla de Registrados (los que solo has visto).
     *
     * @param conexion conexión H2.
     * @param entity Pokémon a guardar.
     */
    fun saveAvistado(conexion: Connection?, entity: Pokemon) {
        val sql = "INSERT INTO REGISTRADOS (nombre, tipo1, tipo2) VALUES (?, ?, ?)"

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

    fun saveCaptura(conexion: Connection?, entity: Pokemon) {
        val sql = "INSERT INTO CAPTURADOS (nombre, tipo1, tipo2) VALUES (?, ?, ?)"

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

    fun liberarPokemonCapturado(conexion: Connection?, id: Int) {
        val sql = "DELETE FROM CAPTURADOS WHERE ID = ?"

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

    fun listarPokemonRegistrados(conexion: Connection?): List<Pokemon>{

        var listaPokemon = mutableListOf<Pokemon>()

        if (conexion != null) {

            val sql = "SELECT * FROM REGISTRADOS"

            try {
                conexion.prepareStatement(sql).use { statement ->

                    val resultSet = statement?.executeQuery()

                    while (resultSet?.next() == true) {

                        val nombre = resultSet.getString("nombre")

                        val tipo1String = resultSet.getString("tipo1")
                        val tipo2String = resultSet.getString("tipo2")

                        val tipo1 = Tipo.valueOf(tipo1String)
                        val tipo2 = Tipo.valueOf(tipo2String)


                        val pokemon = Pokemon(nombre, tipo1, tipo2)

                        listaPokemon.add(pokemon)
                    }
                }
            } catch (e: SQLException) {
                println("Error al listar: ${e.message}")
            }
        }
        return listaPokemon
    }

    fun listarPokemonCapturados(conexion: Connection?): List<Pokemon>{

        var listaPokemon = mutableListOf<Pokemon>()

        if (conexion != null) {

            val sql = "SELECT * FROM CAPTURADOS"

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
        val sql = "UPDATE CAPTURADOS SET tipo1 = ?, tipo2 = ? WHERE nombre = ?"

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

    fun updateRegistrados(conexion: Connection?, pokemon: Pokemon){
        val sql = "UPDATE REGISTRADOS SET tipo1 = ?, tipo2 = ? WHERE nombre = ?"

        if (conexion != null) {
            try {
                conexion.prepareStatement(sql).use { statement ->

                    statement.setString(1, pokemon.tipo1.toString())
                    statement.setString(2, pokemon.tipo2.toString())
                    statement.setString(3, pokemon.nombre)

                    val filasActualizadas = statement.executeUpdate()
                    println("Numero de filas actualizadas: $filasActualizadas")

                }
            } catch (e: SQLException) {
                println("Error al actualizar el Pokémon: ${e.message}")
            }
        }
    }
}