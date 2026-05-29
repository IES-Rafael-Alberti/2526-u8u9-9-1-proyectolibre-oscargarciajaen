package repository

import org.example.Modelo.Pokemon
import repository.Dao.DaoSQL
import java.sql.Connection

class RepositorioSQL(conexion: Connection?) : IRepositorySQL {

    val dao = DaoSQL()
    val connection = conexion

    override fun save(entity: Pokemon) {
        dao.save(connection, entity)
    }

    override fun delete(id: Int) {
        dao.delete(connection, id)
    }

    override fun listarPokemon(): List<Pokemon> {
        val listaPokemon = dao.listarTodos(connection)
        return listaPokemon
    }

    override fun update(entity: Pokemon) {
        dao.update(connection, entity)
    }
}