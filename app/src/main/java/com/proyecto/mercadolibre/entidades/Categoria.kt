package com.proyecto.mercadolibre.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorias")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val idCategoria: Int = 0,

    val nombre: String,
    val logo: String? = null,
    val banner: String? = null
)
