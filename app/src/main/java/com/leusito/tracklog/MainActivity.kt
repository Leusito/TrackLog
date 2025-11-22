package com.leusito.tracklog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.CalendarView //clase para el calendario
import android.widget.EditText //necesario para escribir algo
import android.widget.Toast //muestra mensajes en pantalla
import androidx.appcompat.app.AlertDialog //necesario para escribir algo
import android.content.SharedPreferences //guarda notas en almacenamiento interno
import androidx.core.content.edit

class MainActivity : AppCompatActivity() //representa pantalla principal
{
    private lateinit var calendar: CalendarView //variable para acceder a calendario
    private lateinit var prefs: SharedPreferences //variable para acceder a notas

    override fun onCreate(savedInstanceState: Bundle?) //se ejecuta cuando se abre la app, como el inicio
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //indicamos que diseño xml debe usar Activity
        calendar = findViewById(R.id.calendar) //concetamos la varibale con el CalendarView de xml
        prefs = getSharedPreferences("Entrenos", MODE_PRIVATE) //creamos/abrimos archivo de notas
        calendar.setOnDateChangeListener {_, year, month, dayOfMonth -> //detectar cuando se selecciona una fecha
            val fecha = "$dayOfMonth/${month+1}/$year" //crear cadena de fecha
            val entrenoGuardado = obtenerEntreno(fecha) //recuperar entreno para esa fecah si lo hay
            abrirDialogoEditar(fecha, entrenoGuardado) //mostramos dialogo apra escribri algo
        }
    }

    private fun abrirDialogoEditar(fecha: String, entrenoGuardado: String?) //muestra cuadro de texto para escribir, ? es que puede ser null
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Entreno de $fecha")

        //campo de texto donde se escribe el entreno
        val input = EditText(this)
        input.hint = "Entreno..."
        input.setText(entrenoGuardado)
        builder.setView(input)

        //boton "guardar" para guardar el entreno en SharedPreferences
        builder.setPositiveButton("Guardar") {_, _ ->
            val entreno = input.text.toString()
            guardarEntreno(fecha, entreno)
            Toast.makeText(this, "Guardado: $fecha -> $entreno", Toast.LENGTH_SHORT).show()
        }

        //boton "Cancelar" para cerrar dialogo sin hacer nada
        builder.setNegativeButton("Cancelar") {dialog, _ ->
            dialog.cancel()
        }

        //mostramos el dialogo
        builder.show()
    }

    private fun guardarEntreno(fecha: String, entreno: String) //guarda entreno asociado a una fecha
    {
        prefs.edit {
            putString(fecha, entreno)
        }
    }

    private fun obtenerEntreno(fecha: String): String? //recupera entreno guardado de una fecha
    {
        return prefs.getString(fecha, "")
    }
}

