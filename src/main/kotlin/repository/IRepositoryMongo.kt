package repository

import org.example.Modelo.Pokemon

interface IRepositoryMongo: IRepository<Pokemon> {
    override fun save(entity: Pokemon)
    fun delete(id: Int)
    fun update(entity: Pokemon)
    fun mostrarPokemonPorTipo(string: String)
}