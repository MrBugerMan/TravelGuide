package com.diplom.travelguide.countrydetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.diplom.travelguide.countries.CountryData
import com.diplom.travelguide.countries.MainActivity
import com.diplom.travelguide.databinding.ActivityCountryDetailsBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class CountryDetails: AppCompatActivity() {
    private lateinit var binding: ActivityCountryDetailsBinding
    private lateinit var mapCountry: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("d1d42e49-be29-4221-b7a0-d7888f5ee8af")
        MapKitFactory.initialize(this)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mapCountry = binding.mapCountry
        mapCountry.map.move(CameraPosition(Point(55.755864, 37.617698), 11.0f, 0.0f, 0.0f), // в Point надо передавать координаты страны!!!
            Animation(Animation.Type.SMOOTH, 300F),
            null
        )

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed()}


        var mList: CountryData?= null

        if(intent.hasExtra(MainActivity.NEXT_SCREEN)){
            mList = intent.getSerializableExtra(MainActivity.NEXT_SCREEN) as CountryData
        }

        if(mList != null){ // добавить проверку на null
            binding.nameCountry.text = mList.country ?: "Not Country"
            Glide.with(binding.flag.context).load("https://flagsapi.com/${mList.iso2}/shiny/64.png").into(binding.flag) // разобраться с кэшированием (вроде работает)
            binding.toolbar.title = mList.country
        }


    }

    // крашется при выходи из этого фктивити в гоавное. Скорее проблема вжизненном цикле
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapCountry.onStart()
    }

    override fun onStop() {
        mapCountry.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}