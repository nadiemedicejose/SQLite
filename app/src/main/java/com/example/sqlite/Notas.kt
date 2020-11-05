package com.example.sqlite

class Notas {
    var notaID: Int?=null
    var titulo: String?=null
    var contenido: String?=null

    constructor(notaID: Int, titulo: String, contenido: String) {
        this.notaID = notaID
        this.titulo = titulo
        this.contenido = contenido
    }



}