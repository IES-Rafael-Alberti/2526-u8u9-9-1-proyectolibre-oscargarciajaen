package util

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import java.lang.Exception
import java.util.logging.Level
import java.util.logging.Logger

class MongoConecctionManager() {

    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null

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