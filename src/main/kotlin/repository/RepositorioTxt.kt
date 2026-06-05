package repository

import model.Pokemon
import repository.Dao.DaoTxt

/**
 * Repositorio que persiste el equipo en un fichero de texto plano.
 * Simplemente delega en el [DaoTxt].
 */
class RepositorioTxt(): IRepositoryTxt{

    val daoTxt = DaoTxt()

    /**
     * Crea el fichero equipo.txt si no existe.
     */
    override fun crear() {
        daoTxt.crear()
    }

    /**
     * Guarda una lista de Pokémon en equipo.txt.
     *
     * @param listaEquipo lista de Pokémon a guardar.
     */
    override fun guardar(listaEquipo: List<Pokemon>) {
        daoTxt.guardar(listaEquipo)
    }
}