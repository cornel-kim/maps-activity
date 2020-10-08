package com.modcom.stagelocator

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //this will help in accessing the phones gps
    private lateinit var fusedLocationClient:FusedLocationProviderClient

    //initialize last location
    private  lateinit var lastlocation:Location

    private fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }//end if
        //make location enabled
        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastlocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)//activity that loads maps bit bit, sections
        // it will access the gps
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Chiromo and move the camera
        val chiromo = LatLng(-1.2714496,36.8044152)
        mMap.addMarker(MarkerOptions().position(chiromo).title("Chiromo bus stop").snippet("To: Kangemi, Town, Kileleshwa, Uthiru")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)))

        val safaricom = LatLng(-1.2714496,36.7967548)
        mMap.addMarker(MarkerOptions().position(safaricom).title("safaricom bus stop").snippet("To: Kangemi, Town, Ruaka, Uthiru, Lavington")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)))

        val ngara = LatLng(-1.2740168,36.8213463)
        mMap.addMarker(MarkerOptions().position(ngara).title("ngara bus stop").snippet("To: Ruaka, Ngara, Town, Kangemi, Limuru")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)))



//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chiromo, 14f))//camera location
        setUpMap()
    }
}