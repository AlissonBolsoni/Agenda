package br.com.alisson.agenda.modelo

import java.io.Serializable

class Prova(val materia: String, val data: String, val topicos: ArrayList<String>): Serializable {


    override fun toString(): String {
        return this.materia
    }

}