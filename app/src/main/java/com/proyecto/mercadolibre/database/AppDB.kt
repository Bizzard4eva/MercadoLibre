package com.proyecto.mercadolibre.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyecto.mercadolibre.dao.CategoriaDAO
import com.proyecto.mercadolibre.dao.FavoritoDAO
import com.proyecto.mercadolibre.dao.ImagenProductoDAO
import com.proyecto.mercadolibre.dao.ProductoDAO
import com.proyecto.mercadolibre.dao.UsuarioDAO
import com.proyecto.mercadolibre.entidades.Categoria
import com.proyecto.mercadolibre.entidades.Favorito
import com.proyecto.mercadolibre.entidades.ImagenProducto
import com.proyecto.mercadolibre.entidades.Producto
import com.proyecto.mercadolibre.entidades.Usuario

@Database(
    entities = [
        Usuario::class,
        Producto::class,
        Categoria::class,
        Favorito::class,
        ImagenProducto::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun productoDAO(): ProductoDAO
    abstract fun categoriaDAO(): CategoriaDAO
    abstract fun favoritoDAO(): FavoritoDAO
    abstract fun imagenProductoDAO() : ImagenProductoDAO
}
