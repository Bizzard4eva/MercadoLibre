package com.proyecto.mercadolibre.ui

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.databinding.ActivityRegistrarProductoBinding
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.utilitarios.Util

class RegistrarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarProductoBinding
    private lateinit var db : AppDatabase
    private var imagenUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@RegistrarProductoActivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO
        Thread {
            val listaCategorias = db.categoriaDAO().listarCategorias()
            runOnUiThread {
                val nombresCategoria = listOf("Seleccione una categoria") + listaCategorias.map { it.nombre }
                val adaptadorSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresCategoria)
                adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategoriaRP.adapter = adaptadorSpinner
            }
        }.start()

        binding.imgProductoRP.setOnClickListener {
            selecionarImagenLauncher.launch("image/*")
        }
        binding.btRegistrarRP.setOnClickListener {
            val nombre = binding.edtNombreProductoRP.text.toString().trim()
            val precio = binding.edtPrecioRP.text.toString().trim()
            val cantidad = binding.edtCantidadRP.text.toString().trim()
            val descripcion = binding.edtDescripcionRP.text.toString().trim()
            val categoriaSeleccionada = binding.spinnerCategoriaRP.selectedItemPosition

            if (nombre.isEmpty() || precio.isEmpty() || cantidad.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (categoriaSeleccionada == 0) {
                Toast.makeText(this, "Seleccione una categoría válida.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Util.showToast(this, "Producto registrado correctamente.")

            binding.edtNombreProductoRP.text.clear()
            binding.edtPrecioRP.text.clear()
            binding.edtCantidadRP.text.clear()
            binding.edtDescripcionRP.text.clear()
            binding.spinnerCategoriaRP.setSelection(0) // Vuelve al primer ítem: "Seleccione categoría"
            binding.imgProductoRP.setImageResource(R.drawable.upload_image)
            imagenUri = null
        }
    }
    private val selecionarImagenLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if(uri != null) {
            imagenUri = uri
            binding.imgProductoRP.setImageURI(uri)
        }
    }
}