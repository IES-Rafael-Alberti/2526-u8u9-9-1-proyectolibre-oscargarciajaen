package repository

import model.Objeto
import util.MongoConecctionManager

interface IRepositoryMongo {
    fun save(entity: Objeto)
    fun delete(nombre: String)
    fun update(nombre: String, cantidad: Int)
    fun mostrarObjetos(): List<Objeto>
}