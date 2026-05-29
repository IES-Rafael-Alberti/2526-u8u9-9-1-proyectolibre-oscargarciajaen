package util

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2ConnectionManager() {

    fun create(): Connection? {
        val url = "jdbc:h2:file:./src/main/kotlin/data/pokemon"
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

    fun createTables(conexion: Connection?) {

        if (conexion != null) {
            try {
                conexion.createStatement().use { statement ->
                    val sql = """
                    CREATE TABLE IF NOT EXISTS Pokemon (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255) NOT NULL,
                        tipo1 VARCHAR(255) NOT NULL,
                        tipo2 VARCHAR(255) NOT NULL
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