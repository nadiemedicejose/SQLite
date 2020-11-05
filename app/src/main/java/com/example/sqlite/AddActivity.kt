package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun btnAdd(view: View) {
        val baseDatos = DBManager(this)

        val values = ContentValues()

        values.put("Titulo", editTextTextTitulo!!.text.toString())
        values.put("Descripcion", editTextTextDescripcion!!.text.toString())

        val ID = baseDatos.insert(values)
        if (ID > 0) {
            Toast.makeText(this, "El registro se ingresó con éxito!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "El registro no se pudo completar!", Toast.LENGTH_LONG).show()
        }
    }
}