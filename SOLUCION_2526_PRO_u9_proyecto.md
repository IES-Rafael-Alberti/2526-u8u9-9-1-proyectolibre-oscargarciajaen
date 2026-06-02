# Solución del proyecto

- **Proyecto:** <!-- Nombre del proyecto --> Gestion de Pokemon y Objetos
- **Alumno/a:** <!-- Nombre y apellidos --> Óscar García Jaén
- **Repositorio:** <!-- URL del repositorio --> https://github.com/IES-Rafael-Alberti/2526-u8u9-9-1-proyectolibre-oscargarciajaen

## 1. Resumen del proyecto

- **Problema que resuelve:** Te permite mantener un control de los Pokemon capturados, avistados y objetos adquiridos, ademas de la creacion de generar equipos aleatorios a partir de los Pokemon captruados.
- **Usuarios principales:** Principalmente, este programa va dirigidos a los jugadores de la franquicia Pokémon.
- **Funcionalidades principales:**
  - Registrar Pokémon
  - Actualizar Pokémon
  - Eliminar Pokémon
  - Listar Pokémon
  - Registrar Objeto
  - Actualizar Cantidad de Objeto
  - Listar objetos
  - Eliminar Objetos
  - Crear Equipo
- **Entidades principales:** 
  - Pokemon (data class) - representa un Pokémon con nombre, tipo1, tipo2 e id opcional.
  - Objeto (data class) - representa un objeto con nombre y cantidad.
  - Tipo (enum) - enum con los 18 tipos de Pokémon + False para los Pokémon con 1 solo tipo.
- **Estructura del proyecto:** 
  - model/ - clases del dominio (Pokemon, objeto, tipo) 
  - respository/Dao/ - capa de acceso a datos con genéricos (Dao<T, ID> abstracto, DaoSQL, DaoMongo, DaoCsv, DaoTxt)
  - repository/ - interfaces de repositorio (IRepositorySQL), IRepositoryMongo, IRepositoryTxt) y sus implementaciones (RepositorioSQL, RepositorioMongo, RepositorioTxt)
  - service/ - logica de negocio (PokedexService)
  - app/ - interfaz de consola (Console)
  - util/ - gestores de conexión (H2ConnectionManager, MongoConecctionManager)

## 2. Instalación y ejecución

```bash
# Comandos necesarios para ejecutar el proyecto
./run.sh
```

- **Requisitos previos:** JDK 21+, MongoDB Atlas (o instancia local) con cadena de conexión
- **Configuración necesaria:** El fichero `src/main/kotlin/data/txt/mongo_config.txt` debe contener la URI de conexión a MongoDB
- **Datos de prueba incluidos:** `src/main/kotlin/data/csv/objetos.csv` con 11 objetos en formato `nombre|cantidad`
- 
## 3. Diseño y modelo

- **clases principales:**
  - `Pokemon` (`model/Pokemon.kt`) – data class con nombre, tipo1, tipo2, id opcional
  - `Objeto` (`model/Objeto.kt`) – data class con nombre y cantidad
  - `Tipo` (`model/Tipo.kt`) – enum con 19 valores (Acero, Agua, ..., False)
  - `Console` (`app/Console.kt`) – object singleton que maneja toda la interacción por consola: menús, lectura y validación de entrada
  - `PokedexService` (`service/PokedexService.kt`) – object singleton que orquesta toda la lógica de la aplicación
  - `Dao<T, ID>` (`repository/Dao/Dao.kt`) – clase abstracta genérica base para todos los DAOs
  - `DaoSQL` (`repository/Dao/DaoSQL.kt`) – CRUD para H2 con PreparedStatement
  - `DaoMongo` (`repository/Dao/DaoMongo.kt`) – CRUD para MongoDB con `insertOne`, `deleteOne`, `updateOne`, `find`
  - `DaoCsv` (`repository/Dao/DaoCsv.kt`) – lectura de `objetos.csv`
  - `DaoTxt` (`repository/Dao/DaoTxt.kt`) – lectura/escritura de `equipo.txt` y `mongo_config.txt`
  - `H2ConnectionManager` (`util/H2ConnectionManager.kt`) – conexión H2 y creación de tablas
  - `MongoConecctionManager` (`util/MongoConecctionManager.kt`) – conexión a MongoDB Atlas
- **Relaciones importantes:** Herencia de `Dao<T, ID>` hacia `DaoSQL`, `DaoMongo`, `DaoCsv`, `DaoTxt`. Interfaces `IRepositorySQL`, `IRepositoryMongo`, `IRepositoryTxt` implementadas por `RepositorioSQL`, `RepositorioMongo`, `RepositorioTxt` respectivamente.

- **Genéricos usados:** `Dao<T, ID>` como clase abstracta genérica donde `T` es la entidad e `ID` el tipo de identificador. `Console.mostrarTodos<T>(lista: List<T>)` como función genérica.

- **Colecciones usadas:**
  - `MutableList<Pokemon>` en `DaoSQL.listarPokemonRegistrados()` y `listarPokemonCapturados()` para resultados de consultas SQL
  - `MutableList<Objeto>` en `DaoCsv.leerObjetos()` y `DaoMongo.obtenerTodos()` para acumular resultados
  - `MutableList<Pokemon>` y `MutableList<Int>` en `PokedexService.crearEquipo()` para generar equipo aleatorio
  - `List<String>` en `Console.solicitarOpcion()` para opciones válidas del menú

- **Principios SOLID aplicados:**
  - **SRP:** `Console` solo gestiona I/O de usuario, `DaoSQL` solo operaciones SQL, `H2ConnectionManager` solo conexión/creación de tablas
  - **DIP:** `PokedexService` depende de las abstracciones `IRepositorySQL`, `IRepositoryMongo`, `IRepositoryTxt`, no de implementaciones concretas
  - **OCP:** Se puede añadir un nuevo tipo de persistencia sin modificar los DAOs existentes

- **Patrones de diseño:**
  - **DAO** – `Dao<T, ID>` y sus subclases (`DaoSQL`, `DaoMongo`, `DaoCsv`, `DaoTxt`) encapsulan el acceso a datos
  - **Repository** – `RepositorioSQL`, `RepositorioMongo`, `RepositorioTxt` median entre el servicio y los DAOs
  - **Singleton** – `Console` y `PokedexService` como `object` de Kotlin

## 4. Persistencia

### Ficheros

- **Ficheros usados:**
  - `objetos.csv` (`src/main/kotlin/data/csv/objetos.csv`) – leído por `DaoCsv.leerObjetos()` usando `useLines`
  - `equipo.txt` (`src/main/kotlin/data/txt/equipo.txt`) – creado por `DaoTxt.crear()`, escrito por `DaoTxt.guardar()` usando `writeText`
  - `mongo_config.txt` (`src/main/kotlin/data/txt/mongo_config.txt`) – leído por `DaoTxt.leerConexionMongo()` usando `readLines()`
- **Errores controlados:** `DaoCsv` captura `Exception` genérica, `DaoTxt` captura `Exception` genérica

### MongoDB

- **Base de datos:** proyecto
- **Colecciones:** Objetos – documentos con `nombre` (String) y `cantidad` (Int)
- **Operaciones:** `DaoMongo.guardarObjeto()`, `DaoMongo.eliminarObjeto()`, `DaoMongo.actualizarCantidad()`, `DaoMongo.obtenerTodos()`
- **Clase responsable:** `DaoMongo` + `RepositorioMongo` delega en él; `MongoConecctionManager.obtenerMongoDB()` gestiona la conexión

### Base de datos relacional (H2)

- **SGBD:** H2 en modo fichero (`jdbc:h2:file:./src/main/kotlin/data/bd/pokemon`)
- **Tablas:**
  - `CAPTURADOS` (id INT AUTO_INCREMENT PK, nombre VARCHAR, tipo1 VARCHAR, tipo2 VARCHAR)
  - `REGISTRADOS` (nombre VARCHAR PK, tipo1 VARCHAR, tipo2 VARCHAR)
- **Script SQL:** En `H2ConnectionManager.createTables()` con `CREATE TABLE IF NOT EXISTS`
- **CRUD completo en DaoSQL:** `saveAvistado()`/`saveCaptura()` (Create), `listarPokemonRegistrados()`/`listarPokemonCapturados()` (Read), `update()`/`updateRegistrados()` (Update), `liberarPokemonCapturado()` (Delete)
- **Consultas parametrizadas:** Todos los SQL usan `PreparedStatement` con `?` (ej. `INSERT INTO CAPTURADOS (nombre, tipo1, tipo2) VALUES (?, ?, ?)`)
- **Gestión de conexión:** `H2ConnectionManager.create()` abre la conexión, se pasa como parámetro a los métodos de `DaoSQL`, y se cierra al finalizar `PokedexService.ejecutar()`. Los `PreparedStatement` se cierran automáticamente con `.use {}`.

## 5. Validaciones y errores

- **Expresiones regulares:**
  - `^\d+$` en `Console.solicitarId()` – valida que el ID sea un número positivo. Válido: `"42"`, Inválido: `"-1"`, `"abc"`
  - `^([1-9]|[1-9][0-9])$` en `Console.solicitarCantidad()` – valida cantidad entre 1 y 99. Válido: `"15"`, Inválido: `"0"`, `"100"`

- **Excepciones controladas:**
  - `SQLException` en `DaoSQL` – capturada en cada método, imprime mensaje de error y continúa
  - `Exception` en `DaoCsv.leerObjetos()` – si falla la lectura del CSV, devuelve lista vacía
  - `Exception` en `DaoTxt` – si falla la operación con ficheros, imprime error
  - `Exception` en `DaoMongo` – capturada en cada operación e imprime error.
  - `SQLException` en `H2ConnectionManager` – si falla la conexión, imprime error.

## 6. Pruebas y evidencias

- **Pruebas automatizadas:** 4 tests en `kotlin.test`
  - `PokemonTest` (3 tests): `toString` sin id, `toString` con id, `id` null tras crear
  - `ObjetoTest` (1 test): `toString` formateado
- **Datos de prueba:** `objetos.csv` con 11 líneas de objetos en formato `nombre|cantidad`
- **Evidencia de ejecución:** <!-- Salida de consola o captura -->
- **Evidencia de ficheros:** <!-- Fichero generado/leído -->
- **Evidencia de MongoDB:** <!-- Inserción/consulta -->
- **Evidencia de SQL:** <!-- CRUD realizado -->

## 7. Refactorización, documentación y Git

- **Refactorizaciones aplicadas:** Separación en capas (`model` → `repository/Dao` → `repository` → `service` → `app`), uso de genéricos en `Dao<T, ID>` para evitar duplicación, interfaces de repositorio para desacoplar
- **Código limpio:** Nombres descriptivos en clases y métodos, responsabilidades únicas (SRP), uso de `data class` para entidades
- **Documentación:** Se ha generado documentación HTML con **Dokka** a partir de comentarios **KDoc** añadidos a todas las clases, funciones y propiedades del proyecto. La documentación se genera ejecutando `./gradlew dokkaHtml` y se encuentra en la carpeta `documentacion/`. Cubre todos los paquetes: `model`, `app`, `service`, `repository`, `repository.Dao` y `util`.
- **Control de versiones:** Historial de commits en la rama principal.

## 8. Problemas encontrados y soluciones

| Problema                                 | Solución aplicada                              | Enlace o evidencia |
|------------------------------------------|------------------------------------------------|--------------------|
| Tenia los datos para acceder a la Mongo. | Introducirlos en un txt desde el cual se leen. | X                  |

## 9. Respuestas a los criterios de evaluación

Completa cada criterio con una respuesta breve (Por ejemplo, si habla de clases puedes listar las mas importantes, y entrar en detalle en alguna), técnica y con enlaces al código.

### 9.1. Diseño general

Aplicación de gestión Pokémon con dos dominios principales: Pokémon y Objetos. Arquitectura en capas: **model** (entidades Pokémon, Objeto, Tipo), **repository/Dao** (acceso a datos genéricos), **service** (lógica de negocio en PokedexService), **app** (interfaz de consola en Console), **util** (gestores de conexión).

Justificación: separación de responsabilidades y facilidad de mantenimiento.

### 9.2. Clases y objetos

- **Pokemon**: data class con propiedades `nombre`, `tipo1`, `tipo2`, `id`, dos constructores (primario y secundario con `id`)
- **Objeto**: data class con `nombre` y `cantidad`
- **Tipo**: enum con 19 valores
- **Console**: object con métodos `solicitarOpcion()`, `solicitarNombrePokemon()`, `solicitarId()`, `solicitarCantidad()`, `mostrarTodos()`, etc.
- **PokedexService**: object con métodos `ejecutar()`, `registrar()`, `eliminar()`, `actualizarPokemon()`, `obtenerLista()`, `ingresarObjeto()`, etc.

### 9.3. Encapsulación y visibilidad

- **Console** usa métodos públicos (`solicitarOpcion`, `solicitarNombrePokemon`, etc.) y privados (`mostrarMenu`, `solicitarTipo`).
- **DaoTxt** tiene `rutaMongoConfig` como propiedad privada (`val` inmutable).
- **RepositoryMongo** tiene `connectionManager` y `daoMongo` como propiedades privadas.
- Las clases de modelo usan `data class` con propiedades públicas, inmutables por defecto con `val`.

### 9.4. Colecciones

- `MutableList<Pokemon>` en `DaoSQL` para resultados de consultas, porque permite añadir elementos dinámicamente. Justificación: se necesita construir la lista incrementalmente al recorrer el `ResultSet`.
- `MutableList<Objeto>` en `DaoCsv` y `DaoMongo`, por el mismo motivo.
- `MutableList<Pokemon>` y `MutableList<Int>` en `PokedexService.crearEquipo()` para generar un equipo aleatorio de hasta 6 Pokémon; la lista de índices permite selección sin repetición.

### 9.5. Genéricos

## 9.5 Genéricos

- `Dao<T, ID>`: clase abstracta genérica que permite reutilizar la misma jerarquía para distintas entidades y tipos de ID. `DaoSQL : Dao<Pokemon, Int>`, `DaoCsv : Dao<Objeto, String>`, `DaoTxt : Dao<Pokemon, String>`, `DaoMongo : Dao<Objeto, String>`.
- `Console.mostrarTodos<T>(lista: List<T>)`: función genérica que imprime cualquier tipo de lista, evitando duplicar código para listas de Pokémon y de objetos.

### 9.6. Herencia, interfaces o clases abstractas

- `Dao<T, ID>` es clase abstracta de la que heredan `DaoSQL`, `DaoCsv`, `DaoTxt`, `DaoMongo`. Ventaja: polimorfismo y código común.
- `IRepositorySQL`, `IRepositoryMongo`, `IRepositoryTxt` son interfaces implementadas por `RepositorioSQL`, `RepositorioMongo`, `RepositorioTxt`. Ventaja: `PokedexService` programa contra interfaces, no contra implementaciones concretas (DIP).

### 9.7. Expresiones regulares

- `^\d+$` en `Console.solicitarId()` valida un ID numérico positivo. Ej.: válido `42`, inválido `-3`.
- `^[1-9][1-9][0-9]` en `Console.solicitarCantidad()` valida cantidad 1-99. Ej.: válido `15`, inválido `0`.
- 
### 9.8. Ficheros

- `DaoCsv.leerObjetos()`: lee `objetos.csv` con `useLines`, separa por `|`, crea objetos `Objeto`.
- `DaoTxt.crear()`: crea `equipo.txt` con `File.createNewFile()` y `mkdirs()` para directorios padres.
- `DaoTxt.guardar()`: lee líneas existentes con `readLines()`, añade nuevas, escribe con `writeText()`.
- `DaoTxt.leerConexionMongo()`: lee `mongo_config.txt` con `readLines()`, devuelve primera línea no vacía.
- Control de errores: try-catch con `Exception` genérica en ambos DAOs.

### 9.9. MongoDB

- Base de datos: `proyecto`, colección: `Objetos`
- Documento: `{ nombre: "Poke Ball", cantidad: 10 }`
- Operaciones en `DaoMongo`: `guardarObjeto()` , `eliminarObjeto()` , `actualizarCantidad()` , `obtenerTodos()` 
- `MongoConnectionManager.obtenerMongoDB()`: lee URI de `mongo_config.txt`, crea cliente con `MongoClients.create()`

### 9.10 Base de datos relacional (H2)

- SGBD: H2 en modo fichero, conexión `jdbc:h2:file:./src/main/kotlin/data/bd/pokemon`
- Tablas: `CAPTURADOS` (id `AUTO_INCREMENT`, nombre, tipo1, tipo2) y `REGISTRADOS` (nombre PK, tipo1, tipo2)
- Creación de tablas en `H2ConnectionManager.createTables()` con `CREATE TABLE IF NOT EXISTS`
- CRUD en `DaoSQL`: `saveAvistado()/saveCaptura()`, `listarPokemonRegistrados()/listarPokemonCapturados()` , `update()/updateRegistrados()`, `liberarPokemonCapturado()`
- Consultas parametrizadas con `PreparedStatement` (ej. `DELETE FROM CAPTURADOS WHERE ID = ?`)
- Conexión gestionada por `H2ConnectionManager.create()`, cierre al finalizar `PokedexService.ejecutar()`
- `PreparedStatement` auto-cerrados con `.use {}`

### 9.11 Excepciones

- `SQLException` capturada en todos los métodos de `DaoSQL` — imprime mensaje y continúa la ejecución
- `Exception` en `DaoCsv` — si falla lectura de CSV, devuelve lista vacía
- `Exception` en `DaoTxt` — si falla operación de fichero, imprime error
- `Exception` en `DaoMongo` — capturada en cada operación, imprime error
- `SQLException` en `H2ConnectionManager` — si falla conexión/creación de tablas, imprime error

### 9.12 SOLID y buenas prácticas

- SRP: `Console` → solo I/O; `DaoSQL` → solo SQL; `DaoMongo` → solo MongoDB; `H2ConnectionManager` → solo conexión/tablas; `MongoConnectionManager` → solo conexión Mongo
- DIP: `PokedexService` usa `IRepositorySQL`, `IRepositoryMongo`, `IRepositoryTxt` como tipos, no las implementaciones concretas
- OCP: la jerarquía `Dao<T, ID>` permite añadir nuevos DAOs sin modificar los existentes
- Buenas prácticas: `data class` para entidades, `object` para singletons, `use {}` para recursos auto-cerrables, nombres descriptivos

### 9.13 Librerías externas

- `kotlin("test")` — framework de tests JUnit Platform
- `com.h2database:h2:2.2.224` — base de datos H2 embebida
- `org.mongodb:mongodb-driver-sync:5.1.0` — driver oficial de MongoDB para operaciones síncronas

### 9.14 Pruebas y evidencias

- 4 tests automatizados con `kotlin.test`:
  - `PokemonTest` (3 tests) — verifica `toString()` y `id` inicial
  - `ObjetoTest` (1 test) — verifica `toString()` formateado
- Datos de prueba: `objetos.csv` con 11 objetos precargados

### 9.15 Refactorización y código limpio

- Extracción de responsabilidades en capas separadas.
- Uso de genéricos en `Dao<T, ID>` para evitar duplicación de CRUD
- Nombres de métodos descriptivos: `saveAvistado`, `liberarPokemonCapturado`, `leerConexionMongo`
- Variables y funciones con nombre auto-explicativo
- Uso de `data class` que proporciona `toString`, `equals`, `hashCode` automáticos

### 9.16 Patrones de diseño

- **DAO** (`Data Access Object`): `Dao<T, ID>` y subclases encapsulan la lógica de acceso a datos. Problema: aislar la capa de persistencia del resto de la aplicación. Ventaja: si cambia el motor de BD, solo se modifica el DAO correspondiente.
- **Repository**: `RepositorioSQL`, `RepositorioMongo`, `RepositorioTxt` median entre servicio y DAOs. Ventaja: abstracción adicional que permite cambiar implementaciones sin afectar al servicio.
- **Singleton**: `Console` y `PokedexService` como `object` de Kotlin. Ventaja: instancia única global sin necesidad de pasar referencias.

### 9.17. Documentación

- **Herramienta:** Dokka (plugin `org.jetbrains.dokka` version `2.0.0` en `build.gradle.kts`)
- **Formato:** Comentarios **KDoc** en el código (`/** ... */`) que Dokka transforma a **HTML** estático
- **Partes documentadas:** Todas las clases, funciones y propiedades de los paquetes `model`, `app`, `service`, `repository`, `repository.Dao` y `util`. Cada método incluye descripción, parámetros (`@param`) y retorno (`@return`)
- **Generación:** Se ejecuta con `./gradlew dokkaHtml` y la salida se escribe en `documentacion/`
- **Ejemplos de KDoc añadidos:**
  - `Console.solicitarCantidad()` – documenta validación con regex `^([1-9]|[1-9][0-9])`
  - `DaoSQL.saveAvistado()` – documenta inserción parametrizada en H2
  - `PokedexService.crearEquipo()` – documenta la lógica de selección aleatoria
- **Contenido generado:** ~130 páginas HTML con navegación por paquetes, clases, métodos y propiedades, incluyendo búsqueda y filtrado

### 9.18. Control de versiones

- Uso reiterado de commits para mantener el control de versiones.

## 10. Conclusiones

- **Qué he aprendido:** He aprendido a hacer una aplicación con varios sistemas de persistencia de forma simultánea. 
- **Qué mejoraría si tuviera más tiempo:** Mejoraria un poco la idea principal gestionando mejor que tipo de información guardo en cada sistema.
- **Decisión técnica más importante:** Utilizar la clase generica `Dao<T, ID>` permitiendo reutilizar el diseño del dao sin duplicar código.

## 11. Autoevaluación

Indica en cada criterio el nivel o puntuación que consideras que has alcanzado. Usa la escala de la guía de evaluación: `0`, `2.5`, `5`, `7.5` o `10`. Justifica siempre la puntuación con evidencias concretas: clases, funciones, commits, capturas, documentación o enlaces al código.

### 11.1 Programacion

| Criterio | Puntuación | Justificación                                                                                                                   |
|---|-----------:|---------------------------------------------------------------------------------------------------------------------------------|
| Completitud requisitos mínimos |        7.5 | POO aplicada con clases, herencia, interfaces, genéricos, colecciones, regex, excepciones, SOLID, librerías externas y pruebas. |
| Acceso a ficheros |        7.5 | Uso de 3 ficheros (CSV, TXT), lectura y escritura, control de errores.                                                          |
| Integración MongoDB |        7.5 | CRUD completo en MongoDB, conexión desde fichero de configuración.                                                              |
| BD relacional y CRUD |        7.5 | H2 con dos tablas relacionadas, CRUD completo, consultas parametrizadas con `PreparedStatement`, cierre de recursos con `.use`. |
| Preguntas evaluación Programación |        7.5 | Preguntas respondidas con enlaces permanentes.                                                                                  |

### 11.2. Entornos de Desarrollo

| Criterio | Puntuación/Nivel | Justificación de la puntuación                                                              |
|----------|------------------|---------------------------------------------------------------------------------------------|
| Refactorización y código limpio | 7.5              | Separación en capas, genéricos para evitar duplicación, SRP aplicado, nombres descriptivos. |
| Patrones de diseño | 7.5              | DAO, Repository y Singleton aplicados correctamente.                                        |
| Documentación | 7.5              | Documentado con dokka.                                                                      |
| Control de versiones | 5                | Registro de commits con la linea de vida de mi proyecto                                     |
| Preguntas evaluación Entornos | 7.5              | Preguntas respondidas con enlaces permanentes.                                              |