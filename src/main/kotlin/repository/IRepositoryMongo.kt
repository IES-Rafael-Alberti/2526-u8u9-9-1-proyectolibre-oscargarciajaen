package repository

import model.Objeto
import util.MongoConecctionManager

/**
 * Contrato del repositorio de objetos que trabaja con MongoDB.
 * Guarda, borra, actualiza y lista objetos del inventario.
 */
interface IRepositoryMongo {
    /**
     * Guarda un objeto en MongoDB.
     */
    fun save(entity: Objeto)

    /**
     * Elimina un objeto por su nombre.
     */
    fun delete(nombre: String)

    /**
     * Actualiza la cantidad de un objeto.
     */
    fun update(nombre: String, cantidad: Int)

    /**
     * Obtiene todos los objetos del inventario.
     */
    fun mostrarObjetos(): List<Objeto>
}