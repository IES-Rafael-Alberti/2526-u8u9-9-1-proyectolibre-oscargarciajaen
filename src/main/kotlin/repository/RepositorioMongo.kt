package repository

import model.Objeto
import repository.Dao.DaoMongo
import util.MongoConecctionManager

class RepositorioMongo : IRepositoryMongo {

    private val connectionManager = MongoConecctionManager()
    private val daoMongo: DaoMongo = DaoMongo()

    override fun save(entity: Objeto) {
        val databaseActiva = connectionManager.obtenerMongoDB()

        if (databaseActiva != null) {
            daoMongo.guardarObjeto(databaseActiva, entity)
        }
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun update(entity: Objeto) {
        TODO("Not yet implemented")
    }

    override fun mostrarObjetos(): List<Objeto> {
        TODO("Not yet implemented")
    }
}