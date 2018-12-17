package br.com.alisson.agenda.modelo

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class Aluno: Serializable{

    @JsonProperty("idCliente")
    var id: Long = 0
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
