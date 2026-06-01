package repository

import model.Objeto
import util.MongoConecctionManager

/**
 * Contrato del repositorio de objetos que trabaja con MongoDB.
 * Guarda, borra, actualiza y lista objetos del inventario.
 */
interface IRepositoryMongo {
    fun save(entity: Objeto)
    fun delete(nombre: String)
    fun update(nombre: String, cantidad: Int)
    fun mostrarObjetos(): List<Objeto>
}