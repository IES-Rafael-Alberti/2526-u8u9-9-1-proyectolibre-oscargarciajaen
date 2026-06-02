package repository

import model.Pokemon

/**
 * Contrato del repositorio de Pokémon que trabaja con SQL (H2).
 * Aquí están las operaciones básicas: guardar, borrar, actualizar y listar.
 */
interface IRepositorySQL {
    /**
     * Guarda un Pokémon en la tabla de registrados.
     */
    fun save(entity: Pokemon)

    /**
     * Elimina un Pokémon capturado por su ID.
     */
    fun delete(id: Int)

    /**
     * Actualiza los tipos de un Pokémon.
     */
    fun update(entity: Pokemon)

    /**
     * Lista todos los Pokémon registrados (avistados).
     */
    fun listarPokemonRegistrados(): List<Pokemon>
}