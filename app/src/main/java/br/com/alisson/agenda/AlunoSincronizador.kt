package br.com.alisson.agenda

import android.content.Context
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.event.AtualizaListaAlunoEvent
import br.com.alisson.agenda.preferences.AlunoPreferences
import br.com.alisson.agenda.retrofit.RetrofitInicializador
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

                val dao = AlunoDao(context)
                if (alunosSync != null) {
                    val versao = alunosSync.momentoDaUltimaModificacao

                    preferences?.salvaVersao(versao)

                    dao.sincroniza(alunosSync.alunos)
                    dao.close()

                    bus?.post(AtualizaListaAlunoEvent())
                }
            }
        }
    }
}