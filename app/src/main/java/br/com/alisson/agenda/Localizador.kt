package br.com.alisson.agenda

import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class Localizador(private val context: Context, private val mapaFragment: MapaFragment) : GoogleApiClient.ConnectionCallbacks, LocationListener {

    override fun onConnected(p0: Bundle?) {
        val request = LocationRequest()
        request.smallestDisplacement = 50F
        request.interval = 1000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onLocationChanged(p0: Location) {
        mapaFragment.centralizaEm(LatLng(p0.latitude, p0.longitude))
    }


    private var client: GoogleApiClient? = null

    init {
        client = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()

        client?.connect()
    }

}