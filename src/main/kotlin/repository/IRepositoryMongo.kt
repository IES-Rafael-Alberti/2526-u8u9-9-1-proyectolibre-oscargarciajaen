package repository

import model.Objeto
import util.MongoConecctionManager

interface IRepositoryMongo {
    fun save(entity: Objeto)
    fun delete(id: Int)
    fun update(entity: Objeto)
    fun mostrarObjetos(): List<Objeto>
}