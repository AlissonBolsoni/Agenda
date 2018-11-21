package br.com.alisson.agenda.modelo

class Aluno (
    val nome: String,
    val endereco: String,
    val telefone: String,
    val site: String,
    val nota: Double
){
    var id: Long = 0
        private set

    constructor(
        id: Long,
        nome: String,
        endereco: String,
        telefone: String,
        site: String,
        nota: Double
        ) : this(nome, endereco, telefone, site, nota){
        this.id = id
    }


    override fun toString(): String {
        return "$id - $nome"
    }


}
