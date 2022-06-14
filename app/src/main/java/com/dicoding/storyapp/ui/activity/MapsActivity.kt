package com.dicoding.storyapp.ui.activity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.local.TokenPreferences
import com.dicoding.storyapp.data.local.User
import com.dicoding.storyapp.data.remote.Result
import com.dicoding.storyapp.data.remote.response.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityMapsBinding
import com.dicoding.storyapp.viewmodel.MapsViewModel
import com.dicoding.storyapp.viewmodelfactory.StoryModelFactory
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels {
        StoryModelFactory.getInstanceStory()
    }
    private lateinit var user: User
    private lateinit var mSystemPreferences: TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mSystemPreferences = TokenPreferences(this)
        user = mSystemPreferences.getToken()
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
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()
        getMyLocation()

        mapsViewModel.getLocation("Bearer ${user.token}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarMaps.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.progressBarMaps.visibility = View.INVISIBLE
                        Toast.makeText(this, "Failed. Cause: ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        binding.progressBarMaps.visibility = View.INVISIBLE
                        val data = result.data
                        Log.d("HELP", data.toString())
                        setMarker(data)
                    }
                }
            }
//            val listUser: List<ListStoryItem> = user
//            Log.d("LISTUSER", listUser.toString())
//            for (location in listUser) {
//                val name = location.name
//                val lat = location.lat
//                val lon = location.lon
//                if (lat != null && lon != null) {
//                    val marker = LatLng(lat, lon)
//                    mMap.addMarker(MarkerOptions().position(marker).title("Marker in $name"))
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
//                }
//            }
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun setMarker(location: List<ListStoryItem>) {
        location.forEach {
            if (it.lat != null && it.lon != null) {
                val name = it.name
                val marker = LatLng(it.lat, it.lon)
                mMap.addMarker(MarkerOptions().position(marker).title("Name : $name"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}

