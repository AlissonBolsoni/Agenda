package br.com.alisson.agenda.firebase

import android.util.Log
import br.com.alisson.agenda.AlunoSincronizador
import br.com.alisson.agenda.dao.AlunoDao
import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.event.AtualizaListaAlunoEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class AgendaMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        val mensagem = p0?.data
        Log.i("mensagem recebida", "$mensagem")

        convertParaAluno(mensagem)
    }

    private fun convertParaAluno(mensagem: Map<String, String>?) {
        val chaveAcesso = "alunoSync"
        if (mensagem != null){
            val json = mensagem[chaveAcesso]
            if (json != null){
                val objectMaper = ObjectMapper()
                try {
                    val alunoSync = objectMaper.readValue<AlunoSync>(json, AlunoSync::class.java)

                    AlunoSincronizador(this).sincroniza(alunoSync)

                    val eventBus = EventBus.getDefault()
                    eventBus.post(AtualizaListaAlunoEvent())
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }
    }

}