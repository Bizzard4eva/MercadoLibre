package com.proyecto.mercadolibre.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proyecto.mercadolibre.entidades.ImagenProducto

@Dao
interface ImagenProductoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertarImagen(imagen: ImagenProducto): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertarImagenes(lista: List<ImagenProducto>): List<Long>

    @Query("SELECT urlImagen FROM ImagenesProducto WHERE idProducto = :productoId")
    fun listarUrlsImagenesDeProducto(productoId: Int): List<String>

    @Query("DELETE FROM ImagenesProducto WHERE idProducto = :productoId")
    fun eliminarImagenesDeProducto(productoId: Int)

    @Query("SELECT urlImagen FROM ImagenesProducto WHERE idProducto = :productoId LIMIT 1")
    fun obtenerPrimeraImagenDeProducto(productoId: Int): String?
}