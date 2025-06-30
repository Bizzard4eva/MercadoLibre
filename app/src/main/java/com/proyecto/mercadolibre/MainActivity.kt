package com.proyecto.mercadolibre

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.databinding.ActivityMainBinding
import com.proyecto.mercadolibre.ui.HomeActivity
import com.proyecto.mercadolibre.ui.LoginActivity
import com.proyecto.mercadolibre.ui.PerfilUsuarioActivity
import com.proyecto.mercadolibre.ui.ProductoActivity
import com.proyecto.mercadolibre.ui.ProductosCategoriaActivity
import com.proyecto.mercadolibre.ui.RegistrarProductoActivity
import com.proyecto.mercadolibre.utilitarios.Util

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //TODO
        splash()
    }

    private fun splash()
    {
        Handler(Looper.getMainLooper()).postDelayed({
            Util.navigateTo(this, HomeActivity::class.java)
            finish()
        }, 4000)
    }


}
