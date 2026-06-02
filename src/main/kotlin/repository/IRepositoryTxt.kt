package repository

import model.Pokemon

/**
 * Contrato del repositorio que trabaja con ficheros de texto.
 * Define lo mínimo que tiene que hacer cualquier implementación.
 */
interface IRepositoryTxt {
    /**
     * Crea el fichero de texto si no existe.
     */
    fun crear()

    /**
     * Guarda una lista de Pokémon en el fichero de texto.
     *
     * @param listaEquipo lista de Pokémon a guardar.
     */
    fun guardar(listaEquipo: List<Pokemon>)
}