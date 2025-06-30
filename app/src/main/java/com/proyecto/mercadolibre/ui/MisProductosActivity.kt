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
import com.proyecto.mercadolibre.databinding.ActivityMisProductosBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class MisProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMisProductosBinding
    private lateinit var db: AppDatabase
    private var usuario: Usuario? = Sesion.usuarioActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMisProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@MisProductosActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // TODO

        if (usuario == null) {
            Util.showToast(this, "Debe iniciar sesión para ver sus productos.")
            finish()
            return
        }
        Thread {
            val productos = db.productoDAO().listarProductosPorVendedor(usuario!!.idUsuario)
            val urls = productos.map {
                db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto).firstOrNull() ?: ""
            }

            runOnUiThread {
                binding.tvTituloMP.text = "Mis Productos"

                // Banner personalizado (puedes usar uno genérico si no tienes uno propio)
                Glide.with(this)
                    .load("https://i.imgur.com/Np5A4EM.png") // banner fijo de ejemplo
                    .placeholder(R.drawable.carrusel_uno)
                    .into(binding.imgBannerMP)

                val adapter = ProductoAdaptador(productos, urls) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }

                binding.recyclerViewMP.adapter = adapter
                binding.recyclerViewMP.layoutManager = LinearLayoutManager(this)
            }
        }.start()


    }
}