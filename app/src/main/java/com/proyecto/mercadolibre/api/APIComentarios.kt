package com.proyecto.mercadolibre.api

import com.proyecto.mercadolibre.entidades.Comentario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object APIComentarios {

    // Crea una instancia de Retrofit con la URL base definida en las constantes,
    // y agrega un convertidor para manejar respuestas JSON (usando Gson)
    private  val builder = Retrofit.Builder().baseUrl(Constantes.rutaws)
        .addConverterFactory(GsonConverterFactory.create())

    // Define la interfaz del servicio web (endpoints disponibles)
    interface servicioWeb{
        // Metodo que realiza una petición GET a "comments"
        // y espera una lista de objetos Comentario como respuesta
        @GET("comments")
        fun getComentarios() : Call<List<Comentario>>
    }

    // Metodo que construye e instancia el servicio web para poder usarlo
    fun build() : servicioWeb {
        return builder.build().create(servicioWeb::class.java)
    }
}