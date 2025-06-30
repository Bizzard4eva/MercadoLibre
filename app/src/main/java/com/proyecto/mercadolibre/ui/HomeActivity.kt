package com.proyecto.mercadolibre.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityHomeBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import java.util.Timer
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.mercadolibre.adaptadores.BannerAdapter
import com.proyecto.mercadolibre.adaptadores.CategoriaAdaptador
import com.proyecto.mercadolibre.entidades.CategoriaHome
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
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_categorias)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listaCategorias = listOf(
            CategoriaHome(1, "Moda", R.drawable.cat_moda),
            CategoriaHome(2, "Hogar", R.drawable.cat_hogar),
            CategoriaHome(3, "Vehículos", R.drawable.cat_vehiculo),
            CategoriaHome(4, "Computación", R.drawable.cat_computacion),
            CategoriaHome(5, "Celulares", R.drawable.cat_celulares)
        )

        recyclerView.adapter = CategoriaAdaptador(listaCategorias)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bannerImages = listOf(
            R.drawable.carrusel_uno,
            R.drawable.carrusel_dos,
            R.drawable.carrusel_tres,
            R.drawable.carrusel_cuatro,
            R.drawable.carrusel_cinco
        )

        val bannerAdapter = BannerAdapter(bannerImages)
        binding.bannerH.adapter = bannerAdapter

        // 🔄 Cambiar automáticamente cada 1 segundo
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (currentPage == bannerImages.size) currentPage = 0
                    binding.bannerH.setCurrentItem(currentPage++, true)
                }
            }
        }, 1000, 3000) // delay 1s, repeat cada 1s

        // TODO

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel() // 🛑 Cancelar el Timer al cerrar la actividad
    }
}