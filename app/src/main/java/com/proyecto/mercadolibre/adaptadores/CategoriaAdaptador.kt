package com.proyecto.mercadolibre.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.entidades.CategoriaHome
import com.proyecto.mercadolibre.ui.ProductosCategoriaActivity

class CategoriaAdaptador(private val lista: List<CategoriaHome>) :
    RecyclerView.Adapter<CategoriaAdaptador.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icono: ImageView = itemView.findViewById(R.id.cat_icon)
        val texto: TextView = itemView.findViewById(R.id.cat_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = lista[position]
        holder.icono.setImageResource(categoria.iconoResId)
        holder.texto.text = categoria.nombre

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductosCategoriaActivity::class.java)
            intent.putExtra("idCategoria", categoria.id) // ✅ Esta es la clave correcta
            intent.putExtra("categoria_nombre", categoria.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lista.size
}