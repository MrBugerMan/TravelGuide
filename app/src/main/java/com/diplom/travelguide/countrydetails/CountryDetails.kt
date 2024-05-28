package com.diplom.travelguide.countrydetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diplom.travelguide.ApiService
import com.diplom.travelguide.citydetails.CityDetails
import com.diplom.travelguide.countries.CountryData
import com.diplom.travelguide.countries.MainActivity
import com.diplom.travelguide.databinding.ActivityCountryDetailsBinding
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
        mapCountry.setOnTouchListener { v, event ->
            // Перехватываем касания для MapView
            binding.scrollView.requestDisallowInterceptTouchEvent(true)
            false
        }

        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cityAdapter = CityAdapter(cityList)
        recyclerView.adapter = cityAdapter


        //getCountries()


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

            GlobalScope.launch(Dispatchers.Main) { // Dispatchers.Main для обновления UI
                try {
                    fetchCities(countryList.iso2) { fetchedCities ->
                        cityAdapter = CityAdapter(cityList) // хз передастся ли просто так, поэтому фигачу адаптер
                        recyclerView.adapter = cityAdapter
                    }
                } catch (e: Exception) {
                    // Обработка ошибки
                    Log.d("Error - coruntines", e.message.toString())
                }
            }

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

    private suspend fun fetchCities(countryCode: String, onDataFetched: (List<CityData>) -> Unit) {
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
    }

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


