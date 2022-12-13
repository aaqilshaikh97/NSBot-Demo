package com.nsbot.demoapp.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.nsbot.demoapp.R
import com.nsbot.demoapp.config.LOGO_URL
import com.nsbot.demoapp.databinding.FragmentMapBinding
import com.nsbot.demoapp.ui.viewModel.MainViewModel

class MapFragment: Fragment(), OnMapReadyCallback {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMapBinding
    private lateinit var currentLocation: LatLng

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initializing Map
        val ai: ApplicationInfo = requireContext().packageManager.getApplicationInfo(requireContext().packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initializing fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // show current location on map
        binding.currentLoc.setOnClickListener {
            Toast.makeText(requireContext(), "Showing current location", Toast.LENGTH_SHORT).show()
            getLastLocation()
        }

        //show store location on map
        binding.storeLocation.setOnClickListener {
            val seller = mainViewModel.appDataHelper.getSeller()
            if (seller.latitude != null && seller.longitude != null) {
                currentLocation = LatLng(seller.latitude!!, seller.longitude!!)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLocation))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                Toast.makeText(requireContext(), "Showing store location", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(requireContext(), "Store location is not available", Toast.LENGTH_SHORT).show()
            }
        }

        //show account name and logo
        binding.name.text = mainViewModel.appDataHelper.getBusinessName()

        val logo = mainViewModel.appDataHelper.getSellerLogo()
        if (logo.isNotEmpty()) {
            Glide.with(view).load("$LOGO_URL$logo").into(binding.profile)
        }

        //show profile screen
        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.account.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.name.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        //show setting screen
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        //show store location is available
        val seller = mainViewModel.appDataHelper.getSeller()
        Log.e(TAG, "onMapReady: ${seller.latitude} ${seller.longitude}")
        if (seller.latitude != null && seller.longitude != null) {
            currentLocation = LatLng(seller.latitude!!, seller.longitude!!)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(currentLocation))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
        }else {
            //show current location
            getLastLocation()
        }

    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLocation))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation ?: return
            currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_CODE
        )
    }

    // What must happen when permission is granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    companion object {
        private const val PERMISSION_CODE = 100
        private const val TAG = "MAP"
    }
}