package repository.Dao

import model.Objeto
import java.io.File

/**
 * DAO que lee objetos desde un fichero CSV/TXT con formato "nombre|cantidad".
 * Cada línea del fichero es un objeto separado por pipes.
 */
class DaoCsv : Dao<Objeto, String>() {

    /**
     * Lee el fichero línea a línea y va construyendo objetos [Objeto].
     *
     * @param rutaArchivo ruta al fichero CSV/TXT, por defecto en data/csv/objetos.csv.
     * @return lista de objetos leídos (vacía si el fichero no existe).
     */
    fun leerObjetos(rutaArchivo: String = "./src/main/kotlin/data/csv/objetos.csv"): List<Objeto> {
        val archivo = File(rutaArchivo)
        val objetos = mutableListOf<Objeto>()

        if (!archivo.exists()) {
            println("No se encontró el fichero en: $rutaArchivo")
            return objetos
        }

        try {
            archivo.useLines { lineas ->
                lineas.forEach { linea ->
                    val partes = linea.split("|")
                    if (partes.size == 2) {
                        val nombre = partes[0].trim()
                        val cantidad = partes[1].trim().toIntOrNull() ?: 0
                        objetos.add(Objeto(nombre, cantidad))
                    }
                }
            }
        } catch (e: Exception) {
            println("Error al leer el fichero: ${e.message}")
        }

        return objetos
    }
}
