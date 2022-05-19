package com.speedometer.gpstracker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView

class MyLocation(val context: Context,var textView: TextView) {

    public var locationManager =context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    public fun startSpeedometer() {

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            locationListener
        )

    }
    public val locationListener: LocationListener = object : LocationListener {

        var metersToKm = 3.6F
        override fun onLocationChanged(location: Location) {
            var speed = location!!.speed * metersToKm
            showSpeedometerValue("%.2f".format(speed))
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {
            showSpeedometerValue("00")
        }
    }
    private fun showSpeedometerValue(value: String) {
        textView.text = value
    }
}