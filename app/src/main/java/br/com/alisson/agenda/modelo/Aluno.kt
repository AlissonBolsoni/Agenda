package br.com.alisson.agenda.modelo

import java.io.Serializable

class Aluno: Serializable{

    var id: Long = 0
    var nome: String = ""
    var endereco: String = ""
    var telefone: String = ""
    var site: String = ""
    var nota: Double = 0.0

    override fun toString(): String {
        return "$id - $nome"
    }


}
