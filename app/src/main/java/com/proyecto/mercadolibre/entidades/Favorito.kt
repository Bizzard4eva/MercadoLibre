package com.proyecto.mercadolibre.entidades

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "Favoritos",
    primaryKeys = ["idUsuario", "idProducto"],
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = ["idUsuario"],
        childColumns = ["idUsuario"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Producto::class,
        parentColumns = ["idProducto"],
        childColumns = ["idProducto"],
        onDelete = ForeignKey.CASCADE
    )])
data class Favorito
    (
            var idUsuario : Int,
            var idProducto : Int
    ) { }