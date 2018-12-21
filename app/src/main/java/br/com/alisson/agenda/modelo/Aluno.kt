package br.com.alisson.agenda.modelo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class Aluno: Serializable{

    var id: String = ""
    var nome: String = ""
    var endereco: String = ""
    var telefone: String = ""
    var site: String = ""
    var nota: Double = 0.0
    var caminhoFoto: String? = null
    var desativado: Int = 0
    var sincronizado: Int = 0

    override fun toString(): String {
        return "$id - $nome"
    }

    fun estaDesativado(): Boolean {
        return desativado == 1
    }


}
