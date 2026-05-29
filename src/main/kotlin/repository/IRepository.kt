package repository

interface IRepository<T> {
    fun save(entity: T)
}