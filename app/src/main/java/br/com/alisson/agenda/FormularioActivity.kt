package br.com.alisson.agenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    private var helper: FormularioHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        helper = FormularioHelper(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_formulario, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.menu_formulario_ok -> {
                val aluno = helper!!.pegaAluno()
                val dao = AlunoDao(this)
                dao.insere(aluno)
                dao.close()

                Toast.makeText(this, "Formulario salvo", Toast.LENGTH_SHORT).show()

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
