package br.com.alisson.agenda


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.alisson.agenda.modelo.Prova
import kotlinx.android.synthetic.main.fragment_detalhes_prova.view.*

class DetalhesProvaFragment : Fragment() {

    companion object {
        const val PROVA = "prova"
    }

    var frag: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false)

        frag = view
        val parametros = arguments
        if (parametros != null) {
            val prova = parametros.getSerializable(PROVA) as Prova
            populaCamposCom(prova)
        }


        return view
    }

    fun populaCamposCom(prova: Prova) {
        frag?.detalhes_data?.text = prova.data
        frag?.detalhes_materia?.text = prova.materia

        frag?.detalhes_topicos?.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, prova.topicos)
    }


}
