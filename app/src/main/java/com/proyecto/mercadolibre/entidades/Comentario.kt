package com.proyecto.mercadolibre.entidades

data class Comentario(
    val postId : Int,
    val id : Int,
    val name : String,
    val email : String,
    val body : String
) { }
