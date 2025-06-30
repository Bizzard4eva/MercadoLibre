package com.proyecto.mercadolibre.api

import com.proyecto.mercadolibre.entidades.Comentario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object APIComentarios {

    private  val builder = Retrofit.Builder().baseUrl(Constantes.rutaws)
        .addConverterFactory(GsonConverterFactory.create())

    interface servicioWeb{
        @GET("comments")
        fun getComentarios() : Call<List<Comentario>>
    }
    fun build() : servicioWeb {
        return builder.build().create(servicioWeb::class.java)
    }
}