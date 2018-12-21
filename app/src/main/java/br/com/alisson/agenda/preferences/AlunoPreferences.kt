package br.com.alisson.agenda.preferences

import android.app.Activity
import android.content.Context

class AlunoPreferences(private val context: Context) {
    companion object {
        private const val ALUNO_PREFERENCES = "br.com.alisson.agenda.preferences.AlunoPreferences"
        private const val VERSAO_SP = "VERSAO_SP"
    }

    fun salvaVersao(versao: String) {
        val editor = getSp().edit()
        editor.putString(VERSAO_SP, versao)
        editor.apply()
    }

    fun pegaVersao(): String{
        return getSp().getString(VERSAO_SP, "")?:""
    }

    private fun getSp() = context.getSharedPreferences(ALUNO_PREFERENCES, Activity.MODE_PRIVATE)

}
