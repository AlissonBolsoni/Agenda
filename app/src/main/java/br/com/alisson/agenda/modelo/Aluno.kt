package br.com.alisson.agenda.modelo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
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

    override fun toString(): String {
        return "$id - $nome"
    }


}
