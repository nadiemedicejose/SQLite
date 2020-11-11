package com.example.sqlite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.molde_notas.view.*

class MainActivity (var adapter: NotasAdapter ?= null) : AppCompatActivity() {

    var listaNotas = ArrayList<Notas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cargarQuery("%") // Cualquier titulo

    }

    fun cargarQuery(titulo: String) {
        var baseDatos = DBManager(this)
        val columnas = arrayOf("ID", "Titulo", "Descripcion")
        val selectionArgs = arrayOf(titulo)
        val cursor = baseDatos.query(columnas, "Titulo LIKE ?", selectionArgs, "Titulo")

        listaNotas.clear()

        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val titulo = cursor.getString(cursor.getColumnIndex("Titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndex("Descripcion"))

                listaNotas.add(Notas(ID, titulo, descripcion))
            } while (cursor.moveToNext())
        }

        adapter = NotasAdapter(this, listaNotas)
        listView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val buscar = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        buscar.setSearchableInfo(manejador.getSearchableInfo(componentName))
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                cargarQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.menuAgregar -> {
                var intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner  class NotasAdapter(contexto: Context, var listaNotas: ArrayList<Notas>):BaseAdapter() {

        var contexto: Context ?= contexto

        override fun getCount(): Int {
            return listaNotas.size
        }

        override fun getItem(position: Int): Any {
            return listaNotas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val nota = listaNotas[position]

            val inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var miVista = inflater.inflate(R.layout.molde_notas, null)

            miVista.textViewTitulo.text = nota.titulo!!
            miVista.textViewContenido.text = nota.contenido!!

            miVista.imageViewDelete.setOnClickListener {
                val dbManager = DBManager(this.contexto!!)
                val selectionArgs = arrayOf(nota.notaID.toString())

                dbManager.borrar("ID=?", selectionArgs)

            }

            return miVista
        }

    }

}