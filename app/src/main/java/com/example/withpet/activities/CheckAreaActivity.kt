package com.example.withpet.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.TaskStackBuilder.create
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.MainActivity
import com.example.withpet.R
import com.example.withpet.api.NaverApi
import com.example.withpet.data.address
import com.example.withpet.databinding.ActivityCheckAreaBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.retrofit.RetrofitInstance
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.naver.maps.geometry.LatLng

import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckAreaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityCheckAreaBinding
    private lateinit var naverMap: NaverMap
    private lateinit var viewModel: UserViewModel
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationClient: FusedLocationProviderClient

//등록

    //lateinit: 나중에 초기화 해주겠다는 의미
    var lat: Double = 0.0
    var lng: Double = 0.0

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val api_Key = "7oh71ceh0m"
        private const val api_Secret = "bPiZKoSnIQHRkntF5H6VXrH36l6cnWePeIEhCG5p"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_area)

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        Log.d("main", checkPermissionForLocation(this).toString())

        if(checkPermissionForLocation(this)) {
            startProcess()
        }

        binding.townBtn.setOnClickListener {
            viewModel.updateAddress(this, FBAuth.getUid())
        }
    }

    fun checkPermissionForLocation(context: Context) : Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
                false
            }
        } else {
            true
        }
    }

    fun startProcess() {
        //네이버 맵 동적으로 불러오기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        setUpdateLocationListener()


        naverMap.addOnLocationChangeListener { location ->
            lat = location.latitude
            lng = location.longitude


            var cameraUpdate = CameraUpdate.scrollTo(LatLng(lat, lng))
                .animate(CameraAnimation.Easing)
            naverMap.moveCamera(cameraUpdate)

            val marker = Marker()
            marker.position = LatLng(lat, lng)
            marker.map = naverMap
        }

    }

    val locationCallback : LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult?.let {
                val location = it.locations[it.locations.size-1]

                val myLocation = LatLng(location.latitude, location.longitude)
                val marker = Marker()
                marker.position = myLocation

                marker.map = naverMap
                //마커
                val cameraUpdate = CameraUpdate.scrollTo(myLocation)
                naverMap.moveCamera(cameraUpdate)
                naverMap.maxZoom = 18.0
                naverMap.minZoom = 5.0
                Log.d("main", location.toString())
                getAddress(location)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpdateLocationListener()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 60 * 1000
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }


    fun getAddress(location : Location) {
        val coord = "${location.longitude}, ${location.latitude}"
        val api = RetrofitInstance.getInstance().create(NaverApi::class.java)
        api.getAddress(api_Key, api_Secret, "coordsToaddr", coord, "epsg:4326", "admcode", "json")
            .enqueue(object : Callback<address> {
                override fun onResponse(call: Call<address>, response: Response<address>) {
                    val result = response.body()!!.results[0]

                    viewModel._mutableTown.value = result.region.area3.name
                    viewModel._mutableRegion.value = result.region.area2.name

                    binding.townName.text = result.region.area3.name
                    binding.townViewArea.visibility = View.VISIBLE

                    Log.d("main", result.region.area3.name)
                }

                override fun onFailure(call: Call<address>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }


}