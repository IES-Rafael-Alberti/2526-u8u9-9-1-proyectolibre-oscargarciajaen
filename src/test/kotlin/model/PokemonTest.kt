package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PokemonTest {

    @Test
    fun `toString sin id muestra solo nombre y tipos`() {
        val pokemon = Pokemon(nombre = "Pikachu", tipo1 = Tipo.Electrico, tipo2 = Tipo.False)

        val resultado = pokemon.toString()

        assertEquals("Nombre: Pikachu | Tipo 1: Electrico | Tipo 2: False", resultado)
    }

    @Test
    fun `toString con id muestra el id al principio`() {
        val pokemon = Pokemon(nombre = "Charmander", tipo1 = Tipo.Fuego, tipo2 = Tipo.False, id = 4)

        val resultado = pokemon.toString()

        assertEquals("ID: 4 | Nombre: Charmander | Tipo 1: Fuego | Tipo 2: False", resultado)
    }

    @Test
    fun `pokemon recien creado tiene id null`() {
        val pokemon = Pokemon(nombre = "Bulbasaur", tipo1 = Tipo.Planta, tipo2 = Tipo.Veneno)

        assertNull(pokemon.id)
    }
}
