package com.diplom.travelguide.countrydetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diplom.travelguide.ApiService
import com.diplom.travelguide.citydetails.CityDetails
import com.diplom.travelguide.countries.CountryData
import com.diplom.travelguide.countries.MainActivity
import com.diplom.travelguide.databinding.ActivityCountryDetailsBinding
import com.google.android.play.integrity.internal.t
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

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
                filterList(newText)
                return true
            }
        })


        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed()}


        var countryList: CountryData?= null

        if(intent.hasExtra(MainActivity.COUNTRY_ACTIVITY)){
            countryList = intent.getSerializableExtra(MainActivity.COUNTRY_ACTIVITY) as CountryData
        }

        if(countryList != null){ // добавить проверку на null
            binding.nameCountry.text = countryList.country ?: "Not Country"
            Glide.with(binding.flag.context).load("https://flagsapi.com/${countryList.iso2}/shiny/64.png").into(binding.flag) // разобраться с кэшированием (вроде работает)
            binding.toolbar.title = countryList.country

            getCities(countryList.iso2)


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

    private  fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<CityData>() //  ArrayList<CountriesAndInfoData>()
            for (i in cityList) { // for (i in infoList)
                if (i.city.lowercase().contains(query)) { //  i.mainCountry.name.lowercase().contains(query)
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show()
            } else {
                cityAdapter.setFilteredList(filteredList)
            }
        }
    }

    companion object{
        const val CITY_ACTIVITY = "city_activity"
    }

    private fun getCities(iso2: String){
        ApiService.retrofitService.getCities(iso2).enqueue( object: retrofit2.Callback<ArrayList<CityData>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<CityData>>,
                response: Response<ArrayList<CityData>>
            ) {
                if (response.isSuccessful){
                    cityList.clear()
                    cityList.addAll( response.body() ?: emptyList())

                    cityAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@CountryDetails, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CityData>>, t: Throwable) {
                Log.d("Error - getCity", t.message.toString())
            }

        }

        )
    }
    /*private fun fetchCities(countryCode: String, onDataFetched: (List<CityData>) -> Unit) {
        try {
            ApiService.retrofitService.getCities(countryCode).enqueue(object : retrofit2.Callback<List<CityData>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<List<CityData>>, response: Response<List<CityData>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { cities ->
                            cityList.clear()
                            cityList.addAll(cities)
                            onDataFetched(cityList)

                            cityAdapter.notifyDataSetChanged()
                        }
                    } else {
                        // Обработка ошибки
                        Toast.makeText(this@CountryDetails, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<CityData>>, t: Throwable) {
                    // Обработка ошибки
                    Log.d("Error - getCities", t.message.toString())
                }
            })
        } catch (e: Exception) {
            Log.d("Error - getCities", e.message.toString())
        }
    }*/

    /*fun fetchCities(countryCode: String, onDataFetched: (List<CityData>) -> Unit) {
        ApiService.retrofitService.getCities(countryCode).enqueue(object : retrofit2.Callback<List<CityData>> {
            override fun onResponse(call: Call<List<CityData>>, response: Response<List<CityData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { cities ->
                        cityList.clear()
                        cityList.addAll(cities)
                        onDataFetched(cityList)
                    }
                } else {
                    // Обработка ошибки
                    Toast.makeText(this@CountryDetails, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CityData>>, t: Throwable) {
                // Обработка ошибки
                Log.d("Error - getCities", t.message.toString())
            }
        })
    }*/

    /*private fun getCountries() {
        ApiService.retrofitService.getCountries().enqueue(object : retrofit2.Callback<ArrayList<CountryData>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<CountryData>>,
                response: Response<ArrayList<CountryData>>
            ) {
                if(response.isSuccessful){
                    // Обновление mList с данными из ответа сервера
                    mList.clear()
                    mList.addAll(response.body() ?: emptyList())

                    // Уведомление адаптера об изменениях
                    countryAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                Log.d("Error - getCountries", t.message.toString())
            }

        })
    }*/

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


