package br.com.alisson.agenda

import android.widget.EditText
import android.widget.RatingBar
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioHelper(formularioActivity: FormularioActivity) {

    private var nome: EditText? = null
    private var endereco: EditText? = null
    private var telefone: EditText? = null
    private var site: EditText? = null
    private var rating: RatingBar? = null

    private var aluno: Aluno? = null

    init {
        nome = formularioActivity.none
        endereco = formularioActivity.endereco
        telefone = formularioActivity.telefone
        site = formularioActivity.site
        rating = formularioActivity.rating
        aluno = Aluno()
    }

    fun pegaAluno(): Aluno {
        aluno?.nome = nome!!.text.toString()
        aluno?.endereco = endereco!!.text.toString()
        aluno?.telefone = telefone!!.text.toString()
        aluno?.site = site!!.text.toString()
        aluno?.nota = rating!!.progress.toDouble()

        return aluno!!
    }

    fun preencheFormulario(aluno: Aluno) {
        nome?.setText(aluno.nome)
        endereco?.setText(aluno.endereco)
        telefone?.setText(aluno.telefone)
        site?.setText(aluno.site)
        rating?.progress = aluno.nota.toInt()

        this.aluno = aluno
    }

}