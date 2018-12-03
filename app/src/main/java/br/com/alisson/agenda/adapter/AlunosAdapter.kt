package br.com.alisson.agenda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.alisson.agenda.ImageUtils
import br.com.alisson.agenda.R
import br.com.alisson.agenda.modelo.Aluno
import kotlinx.android.synthetic.main.list_item.view.*

class AlunosAdapter(private val context: Context, private val alunos: ArrayList<Aluno>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val aluno = getItem(position)
        val inflater = LayoutInflater.from(context)

        var view = convertView
        if (view == null)
            view = inflater.inflate(R.layout.list_item, parent, false)

        view!!.item_nome.text = aluno.nome
        view.item_telefone.text = aluno.telefone
        ImageUtils.carregaFoto(view.item_foto, aluno.caminhoFoto, 100, 100)


        view.item_endereco?.text = aluno.endereco
        view.item_site?.text = aluno.site


        return view
    }

    override fun getItem(position: Int) = alunos[position]

    override fun getItemId(position: Int) = alunos[position].id

    override fun getCount() = alunos.size
}