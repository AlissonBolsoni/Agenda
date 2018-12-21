package br.com.alisson.agenda.firebase

import android.util.Log
import br.com.alisson.agenda.retrofit.RetrofitInicializador
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AgendaInstancedIdService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("token Firebase", "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }


    private fun sendRegistrationToServer(refreshedToken: String) {
        val call = RetrofitInicializador.getDispositivoService().enviaToken(refreshedToken)

        call.enqueue(object: Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("token Firebase", "Enviado com falha: " + refreshedToken!!)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i("token Firebase", "Enviado com sucesso: " + refreshedToken!!)
            }
        })
    }
}
