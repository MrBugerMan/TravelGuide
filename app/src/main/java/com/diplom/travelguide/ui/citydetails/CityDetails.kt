package com.diplom.travelguide.ui.citydetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.diplom.travelguide.R
import com.diplom.travelguide.adapters.data.CityData
import com.diplom.travelguide.databinding.ActivityCityDetailsBinding
import com.diplom.travelguide.ui.countrydetails.CountryDetails
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class CityDetails : AppCompatActivity() {

    private lateinit var binding: ActivityCityDetailsBinding
    private lateinit var mapCity: MapView
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        binding = ActivityCityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //val navController = navHostFragment.navController

        //binding.bottomNavMenu.setupWithNavController(navController)

        binding.bottomNavMenu.setupWithNavController(
            navController = navHostFragment.findNavController()
        )


       /* mapCity = binding.mapCity
        mapCity.map.move(CameraPosition(Point(55.755864, 37.617698), 11.0f, 0.0f, 0.0f), // в Point надо передавать координаты города!!!
            Animation(Animation.Type.SMOOTH, 300F),
            null
        )

        // не даём трогать скролингу карту
        mapCity.setOnTouchListener{ v, event ->
            // Перехватываем касания для MapView
            binding.scrollViewMain.requestDisallowInterceptTouchEvent(true)
            false
        }*/

        setSupportActionBar(binding.toolbarCity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbarCity.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }



        var cityList: CityData? = null

        if(intent.hasExtra(CountryDetails.CITY_ACTIVITY)){
            cityList = intent.getSerializableExtra(CountryDetails.CITY_ACTIVITY) as CityData
        }
        if(cityList != null){
            // переносим данные в активити и биндим
            binding.toolbarCity.title = cityList.city
            //binding.cityInfo.text = cityList.info ?: "Not found!"
        }


    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapCity.onStart()
    }

    override fun onStop() {
        mapCity.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}