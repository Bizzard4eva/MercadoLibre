package com.proyecto.mercadolibre.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.proyecto.mercadolibre.entidades.Usuario

@Dao
interface UsuarioDAO {
    @Insert
    fun createUsuario(usuario : Usuario)

    @Update
    fun updateUsuario(usuario: Usuario)

    @Delete
    fun deleteUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    fun validarLogin(correo: String, clave: String): Usuario?

    @Query("SELECT * FROM Usuarios WHERE idUsuario = :id LIMIT 1")
    fun getUsuario(id: Int): Usuario?

}