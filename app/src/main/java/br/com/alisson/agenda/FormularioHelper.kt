package br.com.alisson.agenda

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioHelper(formularioActivity: FormularioActivity) {

    private var foto: ImageView? = null
    private var nome: EditText? = null
    private var endereco: EditText? = null
    private var telefone: EditText? = null
    private var site: EditText? = null
    private var rating: RatingBar? = null

    private var aluno: Aluno? = null

    init {
        foto = formularioActivity.formulario_foto
        nome = formularioActivity.none
        endereco = formularioActivity.endereco
        telefone = formularioActivity.telefone
        site = formularioActivity.site
        rating = formularioActivity.rating
        aluno = Aluno()
    }

    fun pegaAluno(): Aluno {
        aluno?.caminhoFoto = (foto!!.tag as String?)
        aluno?.nome = nome!!.text.toString()
        aluno?.endereco = endereco!!.text.toString()
        aluno?.telefone = telefone!!.text.toString()
        aluno?.site = site!!.text.toString()
        aluno?.nota = rating!!.progress.toDouble()

        return aluno!!
    }

    fun preencheFormulario(aluno: Aluno) {
        ImageUtils.carregaFoto(foto!!,aluno.caminhoFoto)
        nome?.setText(aluno.nome)
        endereco?.setText(aluno.endereco)
        telefone?.setText(aluno.telefone)
        site?.setText(aluno.site)
        rating?.progress = aluno.nota.toInt()

        this.aluno = aluno
    }

    fun carregaFoto(caminhoFoto: String){
        ImageUtils.carregaFoto(foto!!,caminhoFoto)
    }

}