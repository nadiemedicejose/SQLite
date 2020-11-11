package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.widget.Toast
import java.nio.ByteOrder

class DBManager {

    val dbNombre = "MisNotas"
    val dbTabla = "Notas"
    val ColumnaID = "ID"
    val ColumnaTitulo = "Titulo"
    val ColumnaDescripcion = "Descripcion"
    val dbVersion = 1

    val sqlCrearTabla = "CREATE TABLE IF NOT EXISTS " + dbTabla + "(" +
            ColumnaID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ColumnaTitulo + " TEXT NOT NULL," +
            ColumnaDescripcion + " TEXT NOT NULL)"

    var sqlDB : SQLiteDatabase ?= null

    constructor(contexto: Context) {
        val db = DBHelperNotas(contexto)
        sqlDB = db.writableDatabase
    }

    inner class DBHelperNotas(contexto: Context) : SQLiteOpenHelper(contexto, dbNombre, null, dbVersion) {
        var contexto : Context?= contexto

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCrearTabla)
            Toast.makeText(this.contexto, "Base de datos creada con éxito!", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTabla)
        }

    }

    fun insert(values: ContentValues): Long {
        val ID = sqlDB!!.insert(dbTabla, "", values)
        return ID
    }

    fun query(projection: Array<String>, selection: String, selectionArgs: Array<String>, orderBy: String): Cursor {
        val consulta = SQLiteQueryBuilder()
        consulta.tables = dbTabla
        val cursor = consulta.query(sqlDB, projection, selection, selectionArgs, "null", "null", orderBy)
        return cursor
    }

    fun borrar(selection: String, selectionArgs: Array<String>): Int {
        val contador = sqlDB!!.delete(dbTabla, selection, selectionArgs)
        return contador
    }

    fun actualizar(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val contador = sqlDB!!.update(dbTabla, values, selection, selectionArgs)
        return contador
    }

}