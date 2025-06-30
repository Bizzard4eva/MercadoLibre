package com.proyecto.mercadolibre.utilitarios

import android.content.Context
import android.content.Intent
import android.widget.Toast

object Util {

    fun navigateTo(context: Context, destiny: Class<*>)
    {
        val intent = Intent(context, destiny)
        context.startActivity(intent)
    }
    fun navigateDataTo(context: Context, destiny: Class<*>, clave:String, valor:String)
    {
        val intent = Intent(context, destiny)
        intent.putExtra(clave, valor)
        context.startActivity(intent)
    }
    fun navigateDataTo(context: Context, destiny: Class<*>, datos: Map<String, String>)
    {
        val intent = Intent(context, destiny)
        for ((clave, valor) in datos)
        {
            intent.putExtra(clave, valor)
        }
        context.startActivity(intent)
    }
    fun showToast(context: Context, mensaje : String)
    {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}