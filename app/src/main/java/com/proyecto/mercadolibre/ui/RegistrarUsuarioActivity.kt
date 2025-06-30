package com.proyecto.mercadolibre.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityRegistrarUsuarioBinding
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Util

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUsuarioBinding
    private lateinit var db : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@RegistrarUsuarioActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO

        binding.btRegistrarRU.setOnClickListener {
            registrarUsuario()
        }
        binding.btIniciarSesionRU.setOnClickListener {
            Util.navigateTo(this, LoginActivity::class.java)
        }
    }

    private fun registrarUsuario() {
        val nombre = binding.edtNombreRU.text.toString().trim()
        val apellido = binding.edtApellidoRU.text.toString().trim()
        val celular = binding.edtCelularRU.text.toString().trim()
        val correo = binding.edtCorreoRU.text.toString().trim()
        val clave = binding.edtClaveRU.text.toString().trim()

        if (nombre.isEmpty() || apellido.isEmpty() || celular.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario = Usuario(
            nombre = nombre,
            apellido = apellido,
            dni = binding.edtDniRU.text.toString().trim().ifEmpty { null }!!,
            empresa = binding.edtEmpresaRU.text.toString().trim().ifEmpty { null },
            celular = celular,
            correo = correo,
            direccion = binding.edtDireccionRU.text.toString().trim().ifEmpty { null },
            clave = clave
        )
        
        Thread {
            db.usuarioDAO().createUsuario(usuario)
            runOnUiThread {
                Util.showToast(this, "Usuario registrado correctamente!")
            }
        }.start()
    }
}