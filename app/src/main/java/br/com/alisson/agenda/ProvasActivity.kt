package br.com.alisson.agenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.alisson.agenda.modelo.Prova

class ProvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prova)

        val fragmentManager = supportFragmentManager
        val tx = fragmentManager.beginTransaction()
        tx.replace(R.id.frame_principal, ListaProvaFragment())
        if(isLand())
            tx.replace(R.id.frame_secundario, DetalhesProvaFragment())

        tx.commit()

    }

    private fun isLand() = resources.getBoolean(R.bool.land)

    fun selecionaProva(prova: Prova) {
        val fragmentManager = supportFragmentManager
        if(!isLand()){
            val tx = fragmentManager.beginTransaction()

            val fragment = DetalhesProvaFragment()
            val parametros = Bundle()
            parametros.putSerializable(DetalhesProvaFragment.PROVA, prova)
            fragment.arguments = parametros

            tx.replace(R.id.frame_principal, fragment)
            tx.addToBackStack(null)

            tx.commit()
        }else{
            val fragment = fragmentManager.findFragmentById(R.id.frame_secundario) as DetalhesProvaFragment
            fragment.populaCamposCom(prova)

        }
    }
}
