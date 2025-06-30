package com.proyecto.mercadolibre.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
data class Producto
    (
        @PrimaryKey(autoGenerate = true)
            val idProducto: Int,
            val nombre : String,
            val precio : Double,
            val cantidad : Int,
            val idCategoria : Int,
            val descripcion : String? = null,
            val calificacion : Double? = null,
            val idVendedor : Int
    ) { }
 