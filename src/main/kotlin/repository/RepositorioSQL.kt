package repository

import model.Pokemon
import repository.Dao.DaoSQL
import java.sql.Connection

/**
 * Repositorio que trabaja contra la base de datos H2.
 * Básicamente hace de intermediario entre el servicio y el [DaoSQL].
 *
 * @param conexion conexión H2 ya abierta.
 */
class RepositorioSQL(conexion: Connection?) : IRepositorySQL {

    val dao = DaoSQL()
    val connection = conexion

    override fun save(entity: Pokemon) {
        dao.saveAvistado(connection, entity)
    }

    fun saveCaptura(entity: Pokemon) {
        dao.saveAvistado(connection, entity)
        dao.saveCaptura(connection, entity)
    }


    override fun delete(id: Int) {
        dao.liberarPokemonCapturado(connection, id)
    }

    override fun listarPokemonRegistrados(): List<Pokemon> {
        val listaPokemon = dao.listarPokemonRegistrados(connection)
        return listaPokemon
    }

    fun listarPokemonCapturados(): List<Pokemon> {
        val listaPokemon = dao.listarPokemonCapturados(connection)
        return listaPokemon
    }

    override fun update(entity: Pokemon) {
        dao.update(connection, entity)
        dao.updateRegistrados(connection, entity)
    }
}