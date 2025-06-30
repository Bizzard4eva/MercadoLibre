package com.proyecto.mercadolibre.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proyecto.mercadolibre.entidades.Favorito

@Dao
interface FavoritoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun agregarFavorito(favorito: Favorito): Long

    @Delete
    fun deleteFavorito(favorito: Favorito)

    @Query("select * from Favoritos where idUsuario = :userId")
    fun listarFavoritosPorUsuario(userId: Int): List<Favorito>
}