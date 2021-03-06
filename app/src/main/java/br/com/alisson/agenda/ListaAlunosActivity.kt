package br.com.alisson.agenda

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import br.com.alisson.agenda.adapter.AlunosAdapter
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.event.AtualizaListaAlunoEvent
import br.com.alisson.agenda.modelo.Aluno
import br.com.alisson.agenda.retrofit.RetrofitInicializador
import kotlinx.android.synthetic.main.activity_lista_alunos.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAlunosActivity : AppCompatActivity() {

    companion object {
        const val PERMICAO_LIGAR = 123
        const val PERMICAO_SMS = 124
    }

    var alunoClicado: Aluno? = null
    var alunoSync: AlunoSincronizador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        alunoSync = AlunoSincronizador(this)

        swipe_lista_aluno.setOnRefreshListener {
            buscaAlunos()
        }

        novo_aluno.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        lista_alunos.setOnItemClickListener { _, _, position, _ ->
            val aluno = lista_alunos.getItemAtPosition(position) as Aluno
            val intent = Intent(this@ListaAlunosActivity, FormularioActivity::class.java)
            intent.putExtra(FormularioActivity.ALUNO, aluno)
            startActivity(intent)
        }

        registerForContextMenu(lista_alunos)

        buscaAlunos()

        val eventBus = EventBus.getDefault()
        eventBus.register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun atualizaListaAlunoEvent(event: AtualizaListaAlunoEvent){
        if (swipe_lista_aluno.isRefreshing)
            swipe_lista_aluno.isRefreshing = false

        carregaLista()
    }

    private fun buscaAlunos(){
        alunoSync?.buscaAlunos()
    }

    override fun onResume() {
        super.onResume()
        carregaLista()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), PERMICAO_SMS)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_enviar_notas ->{
                EnviaAlunosTask(this).execute()
            }

            R.id.menu_baixar_provas ->{
                val intent = Intent(this, ProvasActivity::class.java)
                startActivity(intent)
            }

            R.id.menu_mapa ->{
                val intent = Intent(this, MapaActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun carregaLista() {
        val dao = AlunoDao(this)
        val alunos = dao.buscaAlunos()
        dao.close()

        val adapter = AlunosAdapter(this, alunos)
        lista_alunos.adapter = adapter
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        alunoClicado = lista_alunos.getItemAtPosition(info.position) as Aluno

        val tel = menu?.add("Ligar")
        tel?.setOnMenuItemClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), PERMICAO_LIGAR)
            } else {
                fazerLigacao(alunoClicado!!)
            }
            false
        }

        val sms = menu?.add("Enviar SMS")
        val intentSms = Intent(Intent.ACTION_VIEW)
        intentSms.data = Uri.parse("sms:${alunoClicado!!.telefone}")
        sms?.intent = intentSms

        val mapa = menu?.add("Ver no Mapa")
        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = Uri.parse("geo:0,0?q=${alunoClicado!!.endereco}")
        mapa?.intent = intentMapa

        var site = alunoClicado!!.site
        if (!site.startsWith("http://") || !site.startsWith("https://"))
            site = "http://$site"

        val vistaSite = menu?.add("Visitar Site")
        val intentSite = Intent(Intent.ACTION_VIEW)
        intentSite.data = Uri.parse(site)
        vistaSite?.intent = intentSite

        val del = menu?.add("Deletar")
        del?.setOnMenuItemClickListener {

            val dao = AlunoDao(this@ListaAlunosActivity)
            dao.deleta(alunoClicado!!)
            dao.close()

            carregaLista()

            alunoSync!!.deleta(alunoClicado!!)

            false
        }
    }


    private fun fazerLigacao(aluno: Aluno) {
        val intentTel = Intent(Intent.ACTION_CALL)
        intentTel.data = Uri.parse("tel:${aluno.telefone}")
        startActivity(intentTel)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMICAO_LIGAR){
            fazerLigacao(alunoClicado!!)
        }else if (requestCode == PERMICAO_SMS){

        }
    }
}
