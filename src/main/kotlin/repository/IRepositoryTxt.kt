package repository

import model.Pokemon

/**
 * Contrato del repositorio que trabaja con ficheros de texto.
 * Define lo mínimo que tiene que hacer cualquier implementación.
 */
interface IRepositoryTxt {
    fun crear()

    fun guardar(listaEquipo: List<Pokemon>)
}