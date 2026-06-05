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

    /**
     * Guarda un Pokémon como avistado en H2.
     */
    override fun save(entity: Pokemon) {
        dao.saveAvistado(connection, entity)
    }

    /**
     * Guarda un Pokémon como avistado y capturado en H2.
     */
    fun saveCaptura(entity: Pokemon) {
        dao.saveAvistado(connection, entity)
        dao.saveCaptura(connection, entity)
    }


    /**
     * Elimina un Pokémon capturado por su ID.
     */
    override fun delete(id: Int) {
        dao.liberarPokemonCapturado(connection, id)
    }

    /**
     * Lista todos los Pokémon registrados (avistados).
     */
    override fun listarPokemonRegistrados(): List<Pokemon> {
        val listaPokemon = dao.listarPokemonRegistrados(connection)
        return listaPokemon
    }

    /**
     * Lista todos los Pokémon capturados.
     */
    fun listarPokemonCapturados(): List<Pokemon> {
        val listaPokemon = dao.listarPokemonCapturados(connection)
        return listaPokemon
    }

    /**
     * Actualiza los tipos de un Pokémon tanto en capturados como en registrados.
     */
    override fun update(entity: Pokemon) {
        dao.update(connection, entity)
        dao.updateRegistrados(connection, entity)
    }
}