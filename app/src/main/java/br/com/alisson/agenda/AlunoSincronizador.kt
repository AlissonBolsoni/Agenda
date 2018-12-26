package br.com.alisson.agenda

import android.content.Context
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.event.AtualizaListaAlunoEvent
import br.com.alisson.agenda.modelo.Aluno
import br.com.alisson.agenda.preferences.AlunoPreferences
import br.com.alisson.agenda.retrofit.RetrofitInicializador
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

open class AlunoSincronizador(private val context: Context) {

    private var preferences: AlunoPreferences? = null

    private var bus: EventBus? = null

    init {
        preferences = AlunoPreferences(context)
        bus = EventBus.getDefault()
    }

    fun buscaAlunos(){
        if (temVersao()){
            buscaNovos(preferences?.pegaVersao())
        }else{
            buscaTodos()
        }
    }

    private fun buscaNovos(versao: String?) {
        if (versao != null){
            val call = RetrofitInicializador.getAlunoService().novos(versao)
            call.enqueue(buscaAlunosCallback())
        }
    }

    private fun buscaTodos() {
        val call = RetrofitInicializador.getAlunoService().lista()
        call.enqueue(buscaAlunosCallback())
    }

    private fun temVersao() = !preferences?.pegaVersao().isNullOrEmpty()

    private fun buscaAlunosCallback(): Callback<AlunoSync> {
        return object : Callback<AlunoSync> {
            override fun onFailure(call: Call<AlunoSync>, t: Throwable) {
                bus?.post(AtualizaListaAlunoEvent())
            }

            override fun onResponse(call: Call<AlunoSync>, response: Response<AlunoSync>) {
                val alunosSync = response.body()

                if (alunosSync != null) {
                    sincroniza(alunosSync)

                    bus?.post(AtualizaListaAlunoEvent())
                    sincronizaAlunosInternos()
                }
            }
        }
    }

    fun sincroniza(alunoSync: AlunoSync) {
        val versao = alunoSync.momentoDaUltimaModificacao

        if (temVersaoNova(versao)){
            preferences?.salvaVersao(versao)

            val dao = AlunoDao(context)
            dao.sincroniza(alunoSync.alunos)
            dao.close()
        }


    }

    private fun temVersaoNova(versao: String): Boolean {

        if (temVersao()) return true

        val spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())

        try {
            val dataServer = spf.parse(versao)
            val versaoLocal = preferences!!.pegaVersao()
            val dataLocal = spf.parse(versaoLocal)

            return dataServer.after(dataLocal)
        } catch (e: Exception){
            e.printStackTrace()
        }


        return false
    }

    private fun sincronizaAlunosInternos(){
        val dao = AlunoDao(context)
        val list  = dao.listaNaoSincronizado()
        dao.close()

        val call = RetrofitInicializador.getAlunoService().atualiza(list)

        call.enqueue(object: Callback<AlunoSync>{
            override fun onResponse(call: Call<AlunoSync>, response: Response<AlunoSync>) {
                val alunoSync = response.body()
                if (alunoSync != null){
                    sincroniza(alunoSync)
                }
            }

            override fun onFailure(call: Call<AlunoSync>, t: Throwable) {

            }
        })
    }

    fun deleta(aluno: Aluno) {
        val call = RetrofitInicializador.getAlunoService().deleta(aluno.id)

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val dao = AlunoDao(context)
                dao.deleta(aluno)
                dao.close()
            }
        })
    }
}