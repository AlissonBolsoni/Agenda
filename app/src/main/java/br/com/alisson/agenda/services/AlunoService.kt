package br.com.alisson.agenda.services

import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.modelo.Aluno
import retrofit2.Call
import retrofit2.http.*

interface AlunoService {

    @POST("aluno")
    fun insere(@Body aluno: Aluno): Call<String>

    @GET("aluno")
    fun lista(): Call<AlunoSync>

    @DELETE("aluno/{id}")
    fun deleta(@Path("id") id: String): Call<Void>

    @GET("aluno/diff")
    fun novos(@Header("datahora") versao: String): Call<AlunoSync>

}