package repository.Dao

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import model.Objeto
import org.bson.Document

/**
 * DAO que habla con MongoDB. Aquí se hacen las operaciones CRUD
 * de los objetos del inventario (insertar, borrar, actualizar, listar).
 */
class DaoMongo : Dao<Objeto, String>() {

    /**
     * Inserta un objeto en la colección "Objetos".
     *
     * @param database base de datos MongoDB activa.
     * @param objeto objeto a guardar.
     */
    fun guardarObjeto(database: MongoDatabase, objeto: Objeto) {
        val coleccion: MongoCollection<Document> = database.getCollection("Objetos")

        val documento: Document = Document()
            .append("nombre", objeto.nombre)
            .append("cantidad", objeto.cantidad)

        try {
            val resultado = coleccion.insertOne(documento)
        } catch (e: Exception) {
            System.err.println("ERROR -> No se pudo insertar ${objeto.nombre}: ${e.message}")
            e.printStackTrace()
        }
    }

    fun eliminarObjeto(database: MongoDatabase?, nombre: String) {
        if (database != null) {
            val coleccion: MongoCollection<Document> = database.getCollection("Objetos")

            try {
                coleccion.deleteOne(Filters.eq("nombre", nombre))
            } catch (e: Exception) {
                println("Error al eliminar: ${e.message}")
            }
        }
    }

    fun actualizarCantidad(database: MongoDatabase?,nombre: String, cantidad: Int) {
        if (database != null) {
            val coleccion: MongoCollection<Document> = database.getCollection("Objetos")

            try {
                val filtro = Filters.eq("nombre", nombre)
                val actualizacion = Updates.set("cantidad", cantidad)
                coleccion.updateOne(filtro, actualizacion)
            } catch (e: Exception) {
                println("Error al actualizar: ${e.message}")
            }
        }
    }

    fun obtenerTodos(database: MongoDatabase?): List<Objeto> {
        val listaObjetos = mutableListOf<Objeto>()
        if (database != null) {
            val coleccion = database.getCollection("Objetos")
            coleccion.find().forEach {
                val objeto = Objeto(nombre = it.getString("nombre"), cantidad = it.getInteger("cantidad"))
                listaObjetos.add(objeto)
            }
        }
        return listaObjetos
    }
}