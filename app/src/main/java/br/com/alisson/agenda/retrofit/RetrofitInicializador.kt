package br.com.alisson.agenda.retrofit

import br.com.alisson.agenda.services.AlunoService
import br.com.alisson.agenda.services.DispositivoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInicializador {

    private var retrofit: Retrofit? = null

    init {

        val intecptor = HttpLoggingInterceptor()
        intecptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(intecptor)

        retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.131:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
    }

    fun getAlunoService() = retrofit!!.create(AlunoService::class.java)

    fun getDispositivoService() = retrofit!!.create(DispositivoService::class.java)


}
