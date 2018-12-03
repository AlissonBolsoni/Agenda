package br.com.alisson.agenda.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.SmsMessage
import android.widget.Toast
import br.com.alisson.agenda.R
import br.com.alisson.agenda.dao.AlunoDao

class SMSReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {

        val pdus = intent?.getSerializableExtra("pdus") as Array<Any>
        val pdu = pdus[0] as ByteArray

        val format = intent.getSerializableExtra("format") as String


        val sms = SmsMessage.createFromPdu(pdu, format)
        val telefone = sms.displayOriginatingAddress

        val dao = AlunoDao(context!!)
        if(dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou SMS $telefone", Toast.LENGTH_SHORT).show()
            val mp = MediaPlayer.create(context!!, R.raw.msg)
            mp.start()
        }

        dao.close()
    }
}