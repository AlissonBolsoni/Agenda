package br.com.alisson.agenda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
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

        lista_alunos.setOnItemClickListener { lista, item, position, id ->
            val aluno = lista_alunos.getItemAtPosition(position) as Aluno
            val intent = Intent(this@ListaAlunosActivity, FormularioActivity::class.java)
            intent.putExtra(FormularioActivity.ALUNO, aluno)
            startActivity(intent)
        }

        registerForContextMenu(lista_alunos)
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
        lista_alunos.adapter = adapter as ListAdapter?
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val del = menu?.add("Deletar")
        del?.setOnMenuItemClickListener {
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            val aluno = lista_alunos.getItemAtPosition(info.position) as Aluno

            val dao = AlunoDao(this)
            dao.deleta(aluno)
            dao.close()

            carregaLista()
            false
        }
    }
}
