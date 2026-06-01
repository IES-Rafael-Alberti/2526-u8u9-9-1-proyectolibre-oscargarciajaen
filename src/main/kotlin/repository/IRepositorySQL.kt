package repository

import model.Pokemon

/**
 * Contrato del repositorio de Pokémon que trabaja con SQL (H2).
 * Aquí están las operaciones básicas: guardar, borrar, actualizar y listar.
 */
interface IRepositorySQL {
    fun save(entity: Pokemon)
    fun delete(id: Int)
    fun update(entity: Pokemon)
    fun listarPokemonRegistrados(): List<Pokemon>



}