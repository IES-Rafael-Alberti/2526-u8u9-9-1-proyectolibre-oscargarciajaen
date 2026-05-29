package repository

import org.example.Modelo.Pokemon
import repository.Dao.DaoSQL

class RepositorioSQL() : IRepositorySQL {

    val dao = DaoSQL()

    override fun save(entity: Pokemon) {
        dao.save(entity)
    }

    override fun delete(id: Int) {
        dao.delete(id)
    }

    override fun listarPokemon(): List<Pokemon> {
        val listaPokemon = dao.listarTodos()
        return listaPokemon
    }

    override fun update(entity: Pokemon) {
        dao.update(entity)
    }
}