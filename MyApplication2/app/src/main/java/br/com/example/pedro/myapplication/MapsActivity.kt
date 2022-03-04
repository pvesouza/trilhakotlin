package br.com.example.pedro.myapplication

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.example.pedro.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    //Variables to request the realtime location of the user
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState: Boolean = false

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE: Int = 1
        const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = MyLocationCallback()

        createLocationRequest()
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
         map = googleMap

/*        val myPlace = LatLng(40.73, -73.99)
        map.addMarker(MarkerOptions().position(myPlace).title("My favourite city"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12.0f))*/

        map.uiSettings.isZoomControlsEnabled = true             // Zoom control of the map
        // Map click listener
        map.setOnMarkerClickListener(this)

        setUpMap()

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdate()
        }
    }

//    Set ups the map in the current location
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12.0f))
                this.map.mapType = GoogleMap.MAP_TYPE_HYBRID
                putMarker(currentLatLong)
            }
        }
    }

    private fun putMarker(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        /*val icon: Bitmap = ContextCompat.getDrawable(this, R.drawable.ic_baseline_place_24)!!.toBitmap() as Bitmap
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))*/
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)
        this.map.addMarker(markerOptions)
    }

    // Get address from a location
    private fun getAddress(location: LatLng): String {
        var address: String
        val geocoder = Geocoder(this, Locale.getDefault())

        val addressList: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        address = addressList[0].getAddressLine(0)
        val city = addressList[0].locality
        val state = addressList[0].adminArea
        val country = addressList[0].countryName
        val postalCode = addressList[0].postalCode
        val countryCode = addressList[0].countryCode

        Log.d("Address", "$city, $state, $country, $postalCode, $countryCode")

        return address
    }

    private fun startLocationUpdate() {
        // Needs user permission, request permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /*Looper*/)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdate()
        }

        task.addOnFailureListener { e->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                }catch (sendEx: IntentSender.SendIntentException) {
                    Toast.makeText(this, R.string.label_location_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class MyLocationCallback(): LocationCallback() {

        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            if (p0 != null) {
                lastLocation = p0.lastLocation
                putMarker(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
    }
}