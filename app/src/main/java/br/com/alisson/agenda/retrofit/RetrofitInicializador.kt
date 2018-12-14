package br.com.alisson.agenda.retrofit

import br.com.alisson.agenda.services.AlunoService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInicializador{

    private var retrofit: Retrofit? = null

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.132:8080/api/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    fun getAlunoService():AlunoService {

        return retrofit!!.create(AlunoService::class.java)

    }


}
