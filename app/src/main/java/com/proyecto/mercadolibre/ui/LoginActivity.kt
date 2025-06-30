package com.proyecto.mercadolibre.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityLoginBinding
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@LoginActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO
        binding.btContinuarLogin.setOnClickListener {
            val correo = binding.edtEmailLogin.text.toString().trim()
            val clave = binding.edtClaveLogin.text.toString().trim()

            if(correo.isEmpty() || clave.isEmpty()) {
                Util.showToast(this, "Debes ingresar correo y clave.")
                return@setOnClickListener
            }

            Thread {
                val usuario = db.usuarioDAO().validarLogin(correo, clave)
                runOnUiThread {
                    if(usuario != null) {
                        Sesion.usuarioActual = usuario
                        Util.showToast(this, "Bienvenido ${usuario.nombre}")
                        Util.navigateTo(this, HomeActivity::class.java)
                        finish()
                    } else {
                        Util.showToast(this, "Correo y/o clave incorrectos")
                    }
                }
            }.start()
        }
        binding.btCrearCuentaLogin.setOnClickListener {
            Util.navigateTo(this, RegistrarUsuarioActivity::class.java)
        }
    }

}