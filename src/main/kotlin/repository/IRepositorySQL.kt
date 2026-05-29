package repository

import model.Pokemon

interface IRepositorySQL {
    fun save(entity: Pokemon)
    fun delete(id: Int)
    fun update(entity: Pokemon)
    fun listarPokemonRegistrados(): List<Pokemon>



}