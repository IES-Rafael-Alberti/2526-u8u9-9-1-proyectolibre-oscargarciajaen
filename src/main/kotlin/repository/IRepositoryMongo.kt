package repository

import model.Pokemon

interface IRepositoryMongo {
    fun save(entity: Pokemon)
    fun delete(id: Int)
    fun update(entity: Pokemon)
    fun mostrarPokemonPorTipo(string: String)
}