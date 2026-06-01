package util

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import java.lang.Exception
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Se encarga de abrir y mantener la conexión con MongoDB Atlas.
 * Funciona como un singleton casero: si ya hay una base de datos
 * cacheada, la devuelve sin volver a conectar.
 */
class MongoConecctionManager() {

    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null

    /**
     * Devuelve la base de datos de MongoDB lista para usar.
     * Si no existe conexión previa, la crea y la guarda.
     *
     * @return la [MongoDatabase] conectada o `null` si algo falla.
     */
    fun obtenerMongoDB(): MongoDatabase? {


        if (database != null) {
            return database!!
        }

        val connectionString = "mongodb+srv://oscargarciajaen:Edu@proyectou8u9.80xr9yq.mongodb.net/?"

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