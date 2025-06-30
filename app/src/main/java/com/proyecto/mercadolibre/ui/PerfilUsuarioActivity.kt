package com.proyecto.mercadolibre.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityPerfilUsuarioBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class PerfilUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilUsuarioBinding
    private lateinit var db : AppDatabase
    private var usuario : Usuario? = Sesion.usuarioActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@PerfilUsuarioActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //TODO
        usuario?.let {
            binding.tvNombrePU.text = "${it.nombre} ${it.apellido}"
            binding.tvCorreoPU.text = it.correo
            binding.tvDniPU.text = it.dni
            binding.tvCelularPU.text = it.celular
            binding.tvEmpresaPU.text = it.empresa
            binding.tvDireccionPU.text = it.direccion
        } ?: run {
            Util.showToast(this, "No has iniciado sesion.")
            Util.navigateTo(this, LoginActivity::class.java)
            finish()
        }
    }
}