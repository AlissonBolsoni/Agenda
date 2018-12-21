package br.com.alisson.agenda.services

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface DispositivoService {

    @POST("firebase/dispositivo")
    fun enviaToken(@Header("token") token: String): Call<Void>

}
