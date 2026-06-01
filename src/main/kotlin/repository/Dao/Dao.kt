package repository.Dao

/**
 * Clase base abstracta común a todos los DAOs del proyecto.
 *
 * Establece una jerarquía común para los DAOs de las distintas
 * persistencias (SQL con H2, MongoDB y ficheros de texto) y
 * aporta una etiqueta legible del origen de los datos que las
 * especializaciones deben aportar.
 *
 * @param T tipo de la entidad gestionada por el DAO.
 * @param ID tipo del identificador de la entidad.
 */
abstract class Dao<T, ID> {

}
