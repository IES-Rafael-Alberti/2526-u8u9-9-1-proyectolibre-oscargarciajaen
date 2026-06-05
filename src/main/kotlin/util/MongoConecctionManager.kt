package util

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import java.lang.Exception
import java.util.logging.Level
import java.util.logging.Logger
import repository.Dao.DaoTxt

/**
 * Se encarga de abrir y mantener la conexión con MongoDB Atlas.
 * Funciona como un singleton casero: si ya hay una base de datos
 * cacheada, la devuelve sin volver a conectar.
 */
class MongoConecctionManager() {

    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null

    private val daoTxt = DaoTxt()

    /**
     * Devuelve la base de datos de MongoDB lista para usar.
     * Si no existe conexión previa, la crea y la guarda.
     *
     * @return la [MongoDatabase] conectada o `null` si algo falla.
     */
    fun obtenerMongoDB(): MongoDatabase? {


        if (database != null) {
            return database
        }

        val connectionString = daoTxt.leerConexionMongo()
            ?: return null

        try {
            Logger.getLogger("org.mongodb.driver").level = Level.OFF
            mongoClient = MongoClients.create(connectionString)
            database = mongoClient?.getDatabase("proyecto")

            return database

        } catch (e: Exception) {
            println("Error al conectar a MongoDB Atlas: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
}