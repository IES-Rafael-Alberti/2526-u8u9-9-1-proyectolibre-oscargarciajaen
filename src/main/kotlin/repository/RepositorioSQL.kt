package repository

import model.Pokemon
import repository.Dao.DaoSQL
import java.sql.Connection

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
        dao.delete(connection, id)
    }

    override fun listarPokemonRegistrados(): List<Pokemon> {
        val listaPokemon = dao.listarPokemonRegistrados(connection)
        return listaPokemon
    }

    override fun update(entity: Pokemon) {
        dao.update(connection, entity)
    }
}