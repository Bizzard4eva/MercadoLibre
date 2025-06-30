package com.proyecto.mercadolibre.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class Usuario
    (
        @PrimaryKey(autoGenerate = true)
            val idUsuario : Int = 0,

            val nombre :String,
            val apellido : String,
            val dni : String,
            val empresa : String? = null,
            val celular : String,
            val correo : String,
            val direccion : String? = null,
            val clave : String
    ) { }