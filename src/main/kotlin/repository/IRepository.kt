package repository

interface IRepository<T> {
    fun save(entity: T): T
    fun delete(id: Int): T?
    fun update(entity: T): T
}