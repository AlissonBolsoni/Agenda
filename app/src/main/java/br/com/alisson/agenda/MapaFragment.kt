package br.com.alisson.agenda

import android.location.Geocoder
import android.os.Bundle
import br.com.alisson.agenda.dao.AlunoDao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapaFragment: SupportMapFragment(), OnMapReadyCallback {

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync(this)
    }

    var mapa: GoogleMap? = null

    override fun onMapReady(p0: GoogleMap?) {
        mapa = p0

        val latLng = pegaCoordenadaDoEndereco("Ozório Zambonini, 254, Planalto Verde, Ribeirão Preto, São Paulo")
        if (latLng != null){
            centralizaEm(latLng)
        }

        val dao = AlunoDao(context!!)
        val alunos = dao.buscaAlunos()
        dao.close()
        for (aluno in alunos){

            val coord = pegaCoordenadaDoEndereco(aluno.endereco)
            if (coord != null){

                val opt = MarkerOptions()
                opt.position(coord)
                opt.title(aluno.nome)
                opt.snippet(aluno.nota.toString())
                p0?.addMarker(opt)
            }
        }
    }

    private fun pegaCoordenadaDoEndereco(endereco: String): LatLng?{

        try {
            val geocoder = Geocoder(context)
            val resultados = geocoder.getFromLocationName(endereco, 1)
            if (resultados.isNotEmpty()){
                return LatLng(resultados[0].latitude, resultados[0].longitude)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return null
    }

    fun centralizaEm(latLng: LatLng) {
        val update = CameraUpdateFactory.newLatLng(latLng)
        mapa?.moveCamera(update)
    }

}