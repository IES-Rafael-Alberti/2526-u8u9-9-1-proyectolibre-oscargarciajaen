package repository

import model.Objeto
import repository.Dao.DaoMongo
import util.MongoConecctionManager

/**
 * Repositorio que trabaja contra MongoDB.
 * Crea su propia conexión con ayuda de [MongoConecctionManager]
 * y delega en el [DaoMongo] para tocar la base de datos.
 */
class RepositorioMongo : IRepositoryMongo {

    private val connectionManager = MongoConecctionManager()
    private val daoMongo: DaoMongo = DaoMongo()

    override fun save(entity: Objeto) {
        val databaseActiva = connectionManager.obtenerMongoDB()
        if (databaseActiva == null) {
            System.err.println("ERROR -> Sin conexión a MongoDB, no se guarda '${entity.nombre}'")
            return
        }
        daoMongo.guardarObjeto(databaseActiva, entity)
    }

    override fun delete(nombre: String) {
        val databaseActiva = connectionManager.obtenerMongoDB()
        daoMongo.eliminarObjeto(databaseActiva, nombre)
    }

    override fun update(nombre: String, cantidad: Int) {
        val databaseActiva = connectionManager.obtenerMongoDB()
        daoMongo.actualizarCantidad(databaseActiva, nombre, cantidad)
    }

    override fun mostrarObjetos(): List<Objeto> {
        val databaseActiva = connectionManager.obtenerMongoDB()
        val lista = daoMongo.obtenerTodos(databaseActiva)
        return lista
    }
}