package br.com.alisson.agenda

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import br.com.alisson.agenda.converter.AlunoConverter
import br.com.alisson.agenda.dao.AlunoDao


class EnviaAlunosTask(private val context: Context): AsyncTask<Void, Void, String>() {

    private var dialog: ProgressDialog? = null

    override fun onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando Alunos...", true, true)
    }

    override fun doInBackground(vararg params: Void?): String? {

        val dao = AlunoDao(context)
        val alunos = dao.buscaAlunos()
        dao.close()

        val converter = AlunoConverter()

        val json = converter.converteParaJSON(alunos)

        val resposta = WebClient.post(json)

        return resposta
    }

    override fun onPostExecute(result: String?) {
        if (!result.isNullOrBlank()) {
            dialog?.dismiss()
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        }
    }

}