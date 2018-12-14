package br.com.alisson.agenda

import java.io.PrintStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object WebClient {

    fun post(json: String): String? {
        return realizaConexao("https://www.caelum.com.br/mobile", json)
    }

    private fun realizaConexao(endereco: String, json: String): String? {
        try {
            val url = URL(endereco)
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-type", "application/json")
            connection.setRequestProperty("Accept", "application/json")

            connection.doOutput = true

            val output = PrintStream(connection.outputStream)
            output.print(json)

            connection.connect()

            val scanner = Scanner(connection.inputStream)
            val resposta = scanner.next()

            return resposta

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }




}