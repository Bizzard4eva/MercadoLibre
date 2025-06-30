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
import com.proyecto.mercadolibre.databinding.ActivityProductosVendedorBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class ProductosVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosVendedorBinding
    private lateinit var db: AppDatabase
    private var usuario: Usuario? = Sesion.usuarioActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductosVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@ProductosVendedorActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //TODO
        val idVendedor = intent.getIntExtra("idVendedor", -1)
        if (idVendedor == -1) {
            Util.showToast(this, "Error al cargar vendedor")
            finish()
            return
        }
        Thread {
            val productos = db.productoDAO().listarProductosPorVendedor(idVendedor)
            val urls = productos.map {
                val lista = db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto)
                lista.firstOrNull() ?: ""
            }

            runOnUiThread {
                binding.tvTituloPV.text = "Productos del Vendedor #$idVendedor"

                Glide.with(this)
                    .load("https://http2.mlstatic.com/storage/splinter-admin/o:f_webp,q_auto:best/1631736883239-headerdesktop.jpg")
                    .placeholder(R.drawable.carrusel_dos)
                    .into(binding.imgBannerPV)

                val adapter = ProductoAdaptador(productos, urls) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }

                binding.recyclerViewPV.adapter = adapter
                binding.recyclerViewPV.layoutManager = LinearLayoutManager(this)
            }
        }.start()
    }
}