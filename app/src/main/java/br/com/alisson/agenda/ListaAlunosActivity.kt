package br.com.alisson.agenda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        novo_aluno.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    private fun carregaLista() {
        val dao = AlunoDao(this)
        val alunos = dao.buscaAlunos()
        dao.close()

        val adapter = ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos)
        lista_alunos.adapter = adapter
    }
}
