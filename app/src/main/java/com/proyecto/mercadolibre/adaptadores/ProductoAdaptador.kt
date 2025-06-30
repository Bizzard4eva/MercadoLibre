package com.proyecto.mercadolibre.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.entidades.Producto

class ProductoAdaptador(
    private val productos: List<Producto>,
    private val urlsImagenes: List<String>,
    private val onItemClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdaptador.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgProducto)
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)

        fun bind(producto: Producto, url: String) {
            nombre.text = producto.nombre
            precio.text = "S/${producto.precio}"
            Glide.with(itemView.context)
                .load(url)
                .placeholder(R.drawable.placeholder_producto)
                .into(img)

            itemView.setOnClickListener {
                onItemClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_destacado, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productos[position], urlsImagenes[position])
      }
    }