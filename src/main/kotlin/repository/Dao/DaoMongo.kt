package repository.Dao

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import model.Objeto
import org.bson.Document

class DaoMongo {

    fun guardarObjeto(database: MongoDatabase, objeto: Objeto) {
        val coleccion: MongoCollection<Document> = database.getCollection("Objetos")

        val documento: Document = Document()
            .append("nombre", objeto.nombre)
            .append("cantidad", objeto.cantidad)

        try {
            coleccion.insertOne(documento)
        } catch (e: Exception) {
            println("Error al guardar: ${e.message}")
        }
    }
}