package model

import kotlin.test.Test
import kotlin.test.assertEquals

class ObjetoTest {

    @Test
    fun `toString muestra nombre y cantidad formateados`() {
        val objeto = Objeto(nombre = "Poke Ball", cantidad = 10)

        val resultado = objeto.toString()

        assertEquals("Nombre: Poke Ball | Cantidad: 10", resultado)
    }
}
