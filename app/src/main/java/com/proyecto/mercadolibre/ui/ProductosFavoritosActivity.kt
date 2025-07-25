package com.proyecto.mercadolibre.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.adaptadores.ProductoAdaptador
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityProductosFavoritosBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class ProductosFavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosFavoritosBinding
    private lateinit var db: AppDatabase
    private var usuario: Usuario? = Sesion.usuarioActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductosFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@ProductosFavoritosActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //TODO

        if (usuario == null) {
            Util.showToast(this, "Debe iniciar sesión")
            finish()
            return
        }
        Thread {
            val favoritos = db.favoritoDAO().listarFavoritosPorUsuario(usuario!!.idUsuario)

            val productos = favoritos.mapNotNull {
                db.productoDAO().getProducto(it.idProducto)
            }

            val urls = productos.map {
                val lista = db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto)
                lista.firstOrNull() ?: ""
            }

            runOnUiThread {
                binding.tvTituloPF.text = "Mis Productos Favoritos"

                Glide.with(this)
                    .load("https://http2.mlstatic.com/storage/splinter-admin/o:f_webp,q_auto:best/1747425559898-41324ba0-3290-11f0-940a-53fc307ffb6d.jpg")
                    .placeholder(R.drawable.carrusel_uno)
                    .into(binding.imgBannerPF)

                val adapter = ProductoAdaptador(productos, urls) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }

                binding.recyclerViewPF.adapter = adapter
                binding.recyclerViewPF.layoutManager = LinearLayoutManager(this)
            }
        }.start()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_favoritos -> {
                    startActivity(Intent(this, ProductosFavoritosActivity::class.java))
                    true
                }
                R.id.nav_registrar -> {
                    startActivity(Intent(this, RegistrarProductoActivity::class.java))
                    true
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, PerfilUsuarioActivity::class.java))
                    true
                }
                else -> false
            }
        }


    }
}