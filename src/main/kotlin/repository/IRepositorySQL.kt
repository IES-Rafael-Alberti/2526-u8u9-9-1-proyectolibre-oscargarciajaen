package repository

import org.example.Modelo.Pokemon

interface IRepositorySQL : IRepository<Pokemon> {
    override fun save(entity: Pokemon)
    fun delete(id: Int)
    fun update(entity: Pokemon)
    fun listarPokemon(): List<Pokemon>



}