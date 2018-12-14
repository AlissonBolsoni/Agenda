package br.com.alisson.agenda.converter

import br.com.alisson.agenda.modelo.Aluno
import com.google.gson.Gson
import org.json.JSONStringer
import java.lang.Exception

class AlunoConverter {
    fun converteParaJSON(alunos: ArrayList<Aluno>): String {
        val js = JSONStringer()

        try {
            js.`object`().key("list").array().`object`().key("aluno").array()
            for (aluno in alunos){
                js.`object`()
                js.key("nome").value(aluno.nome)
                js.key("nota").value(aluno.nota)
                js.endObject()
            }
            js.endArray().endObject().endArray().endObject()
        }catch (e: Exception){
            e.printStackTrace()
        }

        return js.toString()
    }
}