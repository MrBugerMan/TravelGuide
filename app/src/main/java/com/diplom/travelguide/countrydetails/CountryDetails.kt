package com.diplom.travelguide.countrydetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diplom.travelguide.CountriesAndInfoData
import com.diplom.travelguide.citydetails.CityDetails
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var cityList = ArrayList<CityData>()
    private lateinit var cityAdapter: CityAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mapCountry = binding.mapCountry
        mapCountry.map.move(CameraPosition(Point(55.755864, 37.617698), 11.0f, 0.0f, 0.0f), // в Point надо передавать координаты страны!!!
            Animation(Animation.Type.SMOOTH, 300F),
            null
        )

        // не даём скролем касаться карты
        mapCountry.setOnTouchListener { _, _ ->
            // Перехватываем касания для MapView
            binding.scrollView.requestDisallowInterceptTouchEvent(true)
            false
        }

        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cityAdapter = CityAdapter(cityList)
        recyclerView.adapter = cityAdapter



        searchView = binding.searchCity
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CountryDetailsViewModel().filterList(newText, cityList, cityAdapter)
                return true
            }
        })


        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed()}


        var countryList: CountriesAndInfoData?= null  // var countryList: CountryData?= null

        if(intent.hasExtra(MainActivity.COUNTRY_ACTIVITY)){
            countryList = intent.getSerializableExtra(MainActivity.COUNTRY_ACTIVITY) as CountriesAndInfoData // CountryData
        }

        if(countryList != null){ // добавить проверку на null
            binding.nameCountry.text = countryList.mainCountry.name ?: "Not Country"  // countryList.country ?: "Not Country"
            Glide.with(binding.flag.context).load("https://flagsapi.com/${countryList.mainCountry.alpha2Code}/shiny/64.png").into(binding.flag) // countryList.iso2
            binding.toolbar.title =   countryList.mainCountry.name // countryList.country

            CountryDetailsViewModel().getCities(countryList.mainCountry.alpha2Code, cityList, cityAdapter) // countryList.iso2, cityList, cityAdapter

        }

        cityAdapter.setOnClickListener(object:
            CityAdapter.OnClickListener{
            override fun onClick(position: Int, model: CityData) {
                val intent = Intent(this@CountryDetails, CityDetails::class.java)
                intent.putExtra(CITY_ACTIVITY, model)
                startActivity(intent)
            }
        })


    }


    companion object{
        const val CITY_ACTIVITY = "city_activity"
    }


    // крашется при выходи из этого активити в главное. Скорее всего проблема вжизненном цикле
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


