package com.proyecto.mercadolibre.entidades

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ImagenesProducto",
    primaryKeys = ["idProducto", "urlImagen"],
    foreignKeys = [
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["idProducto"],
            childColumns = ["idProducto"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImagenProducto(
    val idProducto: Int,
    val urlImagen: String
)
