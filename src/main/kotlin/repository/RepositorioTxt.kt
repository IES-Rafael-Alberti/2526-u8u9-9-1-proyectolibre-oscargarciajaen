package repository

import model.Pokemon
import repository.Dao.DaoTxt

class RepositorioTxt(): IRepositoryTxt{

    val daoTxt = DaoTxt()

    override fun crear() {
        daoTxt.crear()
    }

    override fun guardar(listaEquipo: List<Pokemon>) {
        daoTxt.guardar(listaEquipo)
    }
}