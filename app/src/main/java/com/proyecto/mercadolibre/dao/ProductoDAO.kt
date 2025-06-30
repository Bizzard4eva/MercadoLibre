package com.proyecto.mercadolibre.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyecto.mercadolibre.entidades.Producto

@Dao
interface ProductoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createProducto(producto: Producto) : Long

    @Update
    fun updateProducto(producto: Producto)

    @Delete
    fun deleteProducto(producto: Producto)

    @Query("select * from Productos where idProducto = :id limit 1")
    fun getProducto(id : Int) : Producto?

    @Query("select * from Productos order by RANDOM()")
    fun listarProductosAleatorios() : List<Producto>

    @Query("select * from Productos where idCategoria = :categoriaId")
    fun listarProductosPorCategoria(categoriaId : Int) : List<Producto>

    @Query("select * from Productos where idVendedor = :vendedorId")
    fun listarProductosPorVendedor(vendedorId : Int) : List<Producto>

    @Query("select * from Productos where nombre like '%' || :texto || '%'")
    fun listarProductosPorNombre(texto : String) : List<Producto>

}