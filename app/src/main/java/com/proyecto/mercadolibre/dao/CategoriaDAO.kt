package com.proyecto.mercadolibre.dao

import androidx.room.Dao
import androidx.room.Query
import com.proyecto.mercadolibre.entidades.Categoria

@Dao
interface CategoriaDAO {

    @Query("select * from Categorias where idCategoria = :id limit 1")
    fun getCategoria(id : Int) : Categoria?

    @Query("select * from Categorias")
    fun listarCategorias() : List<Categoria>
}