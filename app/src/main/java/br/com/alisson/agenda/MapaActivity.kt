package br.com.alisson.agenda

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.maps.SupportMapFragment

class MapaActivity : AppCompatActivity() {

    companion object {
        const val PERMICAO_GPS = 232
    }

    var mapaFragment: MapaFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val manager = supportFragmentManager
        val tx = manager.beginTransaction()
        mapaFragment = MapaFragment()
        tx.replace(R.id.frame_mapa, mapaFragment!!)
        tx.commit()

    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissoes =
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissoes, PERMICAO_GPS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === PERMICAO_GPS) {
            if (grantResults[0] === PackageManager.PERMISSION_GRANTED && grantResults[1] === PackageManager.PERMISSION_GRANTED) {
                Localizador(this, mapaFragment!!)
            }
        }
    }
}
