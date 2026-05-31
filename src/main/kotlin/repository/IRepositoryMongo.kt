package repository

import model.Objeto

interface IRepositoryMongo {
    fun save(entity: Objeto)
    fun delete(id: Int)
    fun update(entity: Objeto)
    fun mostrarObjetos(): List<Objeto>
}