package com.speedometer.gpstracker

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {


    private lateinit var mylocation: MyLocation
    lateinit var speedtxt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        speedtxt =findViewById(R.id.speedtxt)
        mylocation= MyLocation(this,speedtxt)
    }

    override fun onStop() {
        super.onStop()
        if(mylocation!=null)
        mylocation.locationManager.removeUpdates(mylocation.locationListener)
    }

    override fun onResume() {
        super.onResume()
        if(hasGPSPermission())
        {
            if(isLocationEnabled())
            {
                mylocation.startSpeedometer()
            }
            else{
                Toast.makeText(this,"Enable location",Toast.LENGTH_LONG)
            }
        }
        else{
            requestGPSPermission()
        }
    }

    private fun hasGPSPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestGPSPermission() {
        val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permission, 0)
    }
    private fun isLocationEnabled(): Boolean {

        return mylocation.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}