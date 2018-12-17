package br.com.alisson.agenda.services

import br.com.alisson.agenda.dto.AlunoSync
import br.com.alisson.agenda.modelo.Aluno
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlunoService {

    @POST("aluno")
    fun insere(@Body aluno: Aluno): Call<String>

    @GET("aluno")
    fun lista(): Call<AlunoSync>

}