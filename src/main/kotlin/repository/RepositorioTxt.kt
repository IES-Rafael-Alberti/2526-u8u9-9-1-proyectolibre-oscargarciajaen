package repository

import model.Pokemon
import repository.Dao.DaoTxt

/**
 * Repositorio que persiste el equipo en un fichero de texto plano.
 * Simplemente delega en el [DaoTxt].
 */
class RepositorioTxt(): IRepositoryTxt{

    val daoTxt = DaoTxt()

    override fun crear() {
        daoTxt.crear()
    }

    override fun guardar(listaEquipo: List<Pokemon>) {
        daoTxt.guardar(listaEquipo)
    }
}