package com.proyecto.mercadolibre.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.adaptadores.BannerAdapter
import com.proyecto.mercadolibre.adaptadores.CategoriaAdaptador
import com.proyecto.mercadolibre.adaptadores.ProductoAdaptador
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityHomeBinding
import com.proyecto.mercadolibre.entidades.CategoriaHome
import com.proyecto.mercadolibre.entidades.Producto
import java.util.Timer
import java.util.TimerTask

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var currentPage = 0
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Categorías
        val recyclerCategorias = findViewById<RecyclerView>(R.id.recycler_categorias)
        recyclerCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listaCategorias = listOf(
            CategoriaHome(1, "Moda", R.drawable.cat_moda),
            CategoriaHome(2, "Hogar", R.drawable.cat_hogar),
            CategoriaHome(3, "Vehículos", R.drawable.cat_vehiculo),
            CategoriaHome(4, "Computación", R.drawable.cat_computacion),
            CategoriaHome(5, "Celulares", R.drawable.cat_celulares)
        )

        recyclerCategorias.adapter = CategoriaAdaptador(listaCategorias)

        // ✅ Inset Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Carrusel
        val bannerImages = listOf(
            R.drawable.carrusel_uno,
            R.drawable.carrusel_dos,
            R.drawable.carrusel_tres,
            R.drawable.carrusel_cuatro,
            R.drawable.carrusel_cinco
        )

        val bannerAdapter = BannerAdapter(bannerImages)
        binding.bannerH.adapter = bannerAdapter

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (currentPage == bannerImages.size) currentPage = 0
                    binding.bannerH.setCurrentItem(currentPage++, true)
                }
            }
        }, 1000, 3000)

        // ✅ Productos Aleatorios
        Thread {
            val db = DatabaseProvider.getDatabase(this)
            val productos = db.productoDAO().listarProductosAleatorios().take(3)
            val urls = productos.map {
                db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto).firstOrNull() ?: ""
            }

            runOnUiThread {
                val recyclerProductos = findViewById<RecyclerView>(R.id.recycler_productos_destacados)
                recyclerProductos.layoutManager = LinearLayoutManager(this)
                recyclerProductos.adapter = ProductoAdaptador(productos, urls) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }
            }
        }.start()

        // ✅ Más Vendidos
        Thread {
            val db = DatabaseProvider.getDatabase(this)
            val masVendidos = db.productoDAO().listarProductosAleatorios().take(3)
            val urlsMasVendidos = masVendidos.map {
                db.imagenProductoDAO().listarUrlsImagenesDeProducto(it.idProducto).firstOrNull() ?: ""
            }

            runOnUiThread {
                val recyclerMasVendidos = findViewById<RecyclerView>(R.id.recycler_productos_mas_vendidos)
                recyclerMasVendidos.layoutManager = LinearLayoutManager(this)
                recyclerMasVendidos.adapter = ProductoAdaptador(masVendidos, urlsMasVendidos) { producto ->
                    val intent = Intent(this, ProductoActivity::class.java)
                    intent.putExtra("idProducto", producto.idProducto)
                    startActivity(intent)
                }
            }
        }.start()

        // ✅ Barra inferior (BottomNavigationView)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Ya estás en esta pantalla
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

        // Marcar "Inicio" como seleccionado
        bottomNavigation.selectedItemId = R.id.nav_home
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
