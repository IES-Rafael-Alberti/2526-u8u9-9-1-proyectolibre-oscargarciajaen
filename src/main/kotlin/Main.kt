import org.example.Service.PokedexService

/**
 * Punto de entrada de la app.
 * Simplemente instancia el servicio de la Pokédex y lo lanza.
 */
fun main() {
    val service = PokedexService
    service.ejecutar()
}