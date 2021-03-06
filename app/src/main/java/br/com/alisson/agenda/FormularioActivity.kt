package br.com.alisson.agenda

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.activity_formulario.*
import java.io.File
import br.com.alisson.agenda.retrofit.RetrofitInicializador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormularioActivity : AppCompatActivity() {

    companion object {
        const val ALUNO = "ALUNO"
        const val CAMERA_RESULT = 123
    }

    private var helper: FormularioHelper? = null
    private var caminhoFoto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        helper = FormularioHelper(this)

        val aluno = intent.getSerializableExtra(ALUNO) as Aluno?
        if (aluno != null) {
            helper?.preencheFormulario(aluno)
        }

        formulario_camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            caminhoFoto = "${getExternalFilesDir(null)}/${System.currentTimeMillis()}.jpg"
            val arquivoFoto = File(caminhoFoto)

            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider", arquivoFoto
                )
            )

            startActivityForResult(intent, CAMERA_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CAMERA_RESULT && resultCode == Activity.RESULT_OK) {
            helper?.carregaFoto(caminhoFoto)
        }
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
                aluno.desincroniza()
                val dao = AlunoDao(this)
                if (aluno.id == "") {
                    dao.insere(aluno)
                } else {
                    dao.edita(aluno)
                }
                dao.close()

                val call = RetrofitInicializador.getAlunoService().insere(aluno)
                call.enqueue(object: Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                    }

                })

                Toast.makeText(this, "Aluno { ${aluno.nome} } salvo", Toast.LENGTH_SHORT).show()

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
