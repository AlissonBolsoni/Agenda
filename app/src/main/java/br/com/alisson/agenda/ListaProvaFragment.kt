package br.com.alisson.agenda

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.alisson.agenda.modelo.Prova
import kotlinx.android.synthetic.main.fragment_lista_provas.view.*

class ListaProvaFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_lista_provas, container, false)

        val provaPort = Prova("Portugues", "27/05/2016", ArrayList<String>(arrayListOf("Sujeito", "Objeto Direto", "Objeto Indireto")))
        val provaMat = Prova("Matemática", "27/05/2016", ArrayList<String>(arrayListOf("Equações", "Trigonometria")))

        val adapter = ArrayAdapter<Prova>(context, android.R.layout.simple_list_item_1, ArrayList<Prova>(arrayListOf(provaPort, provaMat)))

        view?.provas_lista?.adapter = adapter

        view?.provas_lista?.setOnItemClickListener { parent, view, position, id ->
            val prova = parent.getItemAtPosition(position) as Prova

            val activity = activity as ProvasActivity
            activity.selecionaProva(prova)

        }

        return view
    }
}