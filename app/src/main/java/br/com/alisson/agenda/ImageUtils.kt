package br.com.alisson.agenda

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView

object ImageUtils {

    fun carregaFoto(foto: ImageView, caminhoFoto: String?, width: Int = 512, height: Int = 512){
        if(!caminhoFoto.isNullOrBlank()){
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            if (bitmap != null){
                val bitmapReduzido = Bitmap.createScaledBitmap(bitmap, width, height, true)
                foto.setImageBitmap(bitmapReduzido)
                foto.setBackgroundColor(Color.TRANSPARENT)
                foto.tag = caminhoFoto
                foto.scaleType = ImageView.ScaleType.FIT_XY
            }
        }
    }

}