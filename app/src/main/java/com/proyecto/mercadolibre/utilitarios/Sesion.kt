package com.proyecto.mercadolibre.utilitarios

import com.proyecto.mercadolibre.entidades.Usuario

object Sesion {

    var usuarioActual : Usuario? = null

    fun estaLogueado() : Boolean
    {
        return usuarioActual != null
    }
    fun cerrarSesion()
    {
        usuarioActual = null
    }
}