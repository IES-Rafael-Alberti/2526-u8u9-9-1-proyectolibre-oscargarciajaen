package util

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Gestiona la conexión con la base de datos H2 en modo fichero.
 * Aquí se crea la conexión y se asegura de que existan las tablas
 * que usa la app.
 */
class H2ConnectionManager() {

    /**
     * Abre una conexión contra la base de datos H2 local.
     *
     * @return la [Connection] si todo va bien, o `null` si peta.
     */
    fun create(): Connection? {
        val url = "jdbc:h2:file:./src/main/kotlin/data/bd/pokemon"
        val username = "sa"
        val password = "sa"

        try {
            val connection = DriverManager.getConnection(url, username, password)
            return connection
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Crea las tablas `Capturados` y `Registrados` si todavía no existen.
     *
     * @param conexion conexión H2 sobre la que se ejecutan los CREATE.
     */
    fun createTables(conexion: Connection?) {

        if (conexion != null) {
            try {
                conexion.createStatement().use { statement ->
                    val sql = """
                    CREATE TABLE IF NOT EXISTS Capturados (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(17) NOT NULL,
                        tipo1 VARCHAR(8) NOT NULL,
                        tipo2 VARCHAR(8) NOT NULL
                    );
                    
                    CREATE TABLE IF NOT EXISTS Registrados (
                        nombre VARCHAR(17) PRIMARY KEY,
                        tipo1 VARCHAR(8) NOT NULL,
                        tipo2 VARCHAR(8) NOT NULL
                    );
                """.trimIndent()
                    statement?.execute(sql)
                }
            } catch (e: SQLException) {
                println(e.message)
            }
        }
    }
}