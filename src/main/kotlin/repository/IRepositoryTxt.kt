package repository

import model.Pokemon

interface IRepositoryTxt {
    fun crear()

    fun guardar(listaEquipo: List<Pokemon>)
}