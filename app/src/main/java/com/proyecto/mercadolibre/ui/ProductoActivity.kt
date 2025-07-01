package com.proyecto.mercadolibre.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.proyecto.mercadolibre.R
import com.proyecto.mercadolibre.database.AppDatabase
import com.proyecto.mercadolibre.database.DatabaseProvider
import com.proyecto.mercadolibre.databinding.ActivityProductoBinding
import com.proyecto.mercadolibre.entidades.Favorito
import com.proyecto.mercadolibre.entidades.Producto
import com.proyecto.mercadolibre.entidades.Usuario
import com.proyecto.mercadolibre.utilitarios.Sesion
import com.proyecto.mercadolibre.utilitarios.Util
import android.widget.ArrayAdapter
import android.widget.Toast
import com.proyecto.mercadolibre.api.APIComentarios
import com.proyecto.mercadolibre.api.Constantes
import com.proyecto.mercadolibre.entidades.Comentario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.graphics.Typeface

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding
    private lateinit var db : AppDatabase
    private var usuario : Usuario? = Sesion.usuarioActual


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseProvider.getDatabase(this@ProductoActivity)

        val idProducto = intent.getIntExtra("idProducto", -1)
        if(idProducto == -1) {
            Util.showToast(this, "Error al cargar el producto")
            finish()
            return
        }

        Thread {
            val producto = db.productoDAO().getProducto(idProducto)
            val listaUrls = db.imagenProductoDAO().listarUrlsImagenesDeProducto(idProducto)

            if(producto == null) {
                runOnUiThread {
                    Util.showToast(this, "Producto no encontrado")
                    finish()
                }
                return@Thread
            }
            runOnUiThread {
                cargarDatosProducto(producto, listaUrls)
            }
        }.start()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // TODO
        binding.btComprarP.setOnClickListener {
            if(Sesion.estaLogueado()) {
                Util.navigateTo(this, ComprarProductoActivity::class.java)
            } else {
                Util.navigateTo(this, LoginActivity::class.java)
            }
        }
        binding.btAgregarFavoritoP.setOnClickListener {
            if (Sesion.estaLogueado()) {
                Thread {
                    val favorito = Favorito(
                        idUsuario = usuario!!.idUsuario,
                        idProducto = intent.getIntExtra("idProducto", -1)
                    )
                    db.favoritoDAO().agregarFavorito(favorito)
                    runOnUiThread {
                        Util.showToast(this, "Producto agregado a Favoritos")
                    }
                }.start()
            } else {
                Util.navigateTo(this, LoginActivity::class.java)
            }
        }

        traerComentarios()
    }

    // Metodo para traer los comentarios de la api
    private fun traerComentarios() {
        // Crear una solicitud a la API para obtener la lista de comentarios
        val request = APIComentarios.build().getComentarios()

        // Ejecutar la solicitud de forma asíncrona
        request.enqueue(object : Callback<List<Comentario>> {
            // Si la respuesta fue exitosa
            override fun onResponse(
                call: Call<List<Comentario>>,
                response: Response<List<Comentario>>
            ) {
                // Obtener la lista de comentarios de la respuesta
                val lista = response.body()

                // Verificar que la lista no sea nula ni vacía
                if (lista != null && lista.isNotEmpty()) {
                    // Generar un número aleatorio entre 1 y 7 para definir cuántos comentarios mostrar
                    val cantidadAleatoria = (1..7).random()

                    // Guardar esta cantidad en una constante global (opcional)
                    Constantes.cant_comentarios = cantidadAleatoria

                    // Mezclar aleatoriamente la lista y tomar solo la cantidad aleatoria
                    val listaAleatoria = lista.shuffled().take(cantidadAleatoria)

                    // Crear una lista de CharSequence (permite aplicar estilos como negrita)
                    val resultado = ArrayList<CharSequence>()

                    // Recorrer la lista de comentarios seleccionados y formatear el texto
                    listaAleatoria.forEach {
                        val texto = SpannableStringBuilder()

                        texto.append("Posteado por: ", StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        texto.append("${it.id}\n")

                        texto.append("Email: ", StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        texto.append("${it.email}\n\n")

                        texto.append("Mensaje: ", StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        texto.append("${it.body}\n")

                        // Añadir el texto formateado a la lista
                        resultado.add(texto)
                    }

                    // Actualizar el TextView del título con la cantidad actual de comentarios
                    binding.tvComentariosTitulo.text = "Comentarios (${listaAleatoria.size})"

                    // Crear un adaptador para mostrar los comentarios con estilo enriquecido
                    val adaptador = ArrayAdapter(
                        this@ProductoActivity,
                        android.R.layout.simple_list_item_1,
                        resultado
                    )

                    // Asignar el adaptador al ListView para mostrar los datos
                    binding.lvComentarios.adapter = adaptador
                }
            }

            // Si ocurre un error durante la solicitud (fallo de red, etc.)
            override fun onFailure(call: Call<List<Comentario>>, t: Throwable) {
                Toast.makeText(this@ProductoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarDatosProducto(producto: Producto, imagenes : List<String>) {
        binding.tvNombreP.text = producto.nombre
        binding.tvPrecioP.text = producto.precio.toString()
        binding.tvCalificacionP.text = producto.calificacion.toString()
        binding.tvDescripcionP.text = producto.descripcion

        if(imagenes.isNotEmpty()) {
            Glide.with(this)
                .load(imagenes[0])
                .placeholder(R.drawable.mercadologo)
                .error(R.drawable.upload_image)
                .into(binding.imgProductoP)
        }
    }


}