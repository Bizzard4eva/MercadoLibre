package com.proyecto.mercadolibre.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.adaptadores.ProductoAdaptador
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityProductosCategoriaBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class ProductosCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosCategoriaBinding
    private lateinit var db: AppDatabase
    private var usuario: Usuario? = Sesion.usuarioActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductosCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idCategoria = intent.getIntExtra("idCategoria", -1)
        if (idCategoria == -1) {
            Util.showToast(this, "Error al cargar categoría")
            finish()
            return
        }

        Thread {
            val categoria = db.categoriaDAO().getCategoria(idCategoria)
            val productos = db.productoDAO().listarProductosPorCategoria(idCategoria)
            val urls = productos.map {
                val lista = db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto)
                lista.firstOrNull() ?: ""
            }

            runOnUiThread {
                // ✅ Título de la categoría
                binding.tvTitulo.text = "Productos de ${categoria?.nombre ?: "Categoría"}"

                // ✅ Banner de la categoría
                Glide.with(this)
                    .load(categoria?.banner)
                    .placeholder(R.drawable.carrusel_cinco)
                    .into(binding.imgBannerPC)

                // ✅ RecyclerView de productos
                val adapter = ProductoAdaptador(productos, urls) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }

                binding.recyclerViewPC.adapter = adapter
                binding.recyclerViewPC.layoutManager = LinearLayoutManager(this)
            }
        }.start()
    }
}
