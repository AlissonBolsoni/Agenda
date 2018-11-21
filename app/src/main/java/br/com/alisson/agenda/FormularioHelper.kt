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

    init {
        nome = formularioActivity.none
        endereco = formularioActivity.endereco
        telefone = formularioActivity.telefone
        site = formularioActivity.site
        rating = formularioActivity.rating
    }

    fun pegaAluno(): Aluno{
        return Aluno(
            nome!!.text.toString(),
            endereco!!.text.toString(),
            telefone!!.text.toString(),
            site!!.text.toString(),
            rating!!.progress.toDouble()
        )
    }

}