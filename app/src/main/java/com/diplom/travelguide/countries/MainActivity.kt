package com.diplom.travelguide.countries

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplom.travelguide.ApiService
import com.diplom.travelguide.CountriesAndInfoData
import com.diplom.travelguide.countrydetails.CountryDetails
import com.diplom.travelguide.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<CountryData>()
    private lateinit var countryAdapter: CountryAdapter

    private var infoList = ArrayList<CountriesAndInfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // устанавливаем ключь для API Яндекс.Карт
        MapKitFactory.setApiKey("d1d42e49-be29-4221-b7a0-d7888f5ee8af")


        // поиск
        searchView = binding.searchCountry
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        // настройка RecyclerView
        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(mList)
        //countryAdapter = CountryAdapter(infoList)
        recyclerView.adapter = countryAdapter

        // получаем данные из API (список стран и их индификатор для скачивания картинок))
        getCountries()



        //getAllCountriesAndInfo()



        // передача данных в новое активити и переход в новое активити при нажатии на элемент RecyclerView
        countryAdapter.setOnClickListener(object:
            CountryAdapter.OnClickListener {
            override fun onClick(position: Int, model: CountryData) { // model: CountriesAndInfoData
                val intent = Intent(this@MainActivity, CountryDetails::class.java)
                intent.putExtra(COUNTRY_ACTIVITY, model)
                startActivity(intent)
            }
        })

    }

    private fun getAllCountriesAndInfo() {
        ApiService.retrofitServiceSecond.getAllCountriesAndInfo().enqueue(object : retrofit2.Callback<ArrayList<CountriesAndInfoData>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<CountriesAndInfoData>>,
                response: Response<ArrayList<CountriesAndInfoData>>
            ) {
                if(response.isSuccessful){
                    // Обновление mList с данными из ответа сервера
                    infoList.clear()
                    infoList.addAll(response.body() ?: emptyList())

                    // Уведомление адаптера об изменениях
                    countryAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CountriesAndInfoData>>, t: Throwable) {
                Log.d("Error - getAllCountriesAndInfo", t.message.toString())
            }

        })
    }

    // функция получения данных стран из API
    private fun getCountries() {
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
    }



    companion object{
        const val COUNTRY_ACTIVITY = "details_screen"}



    // функция фильтрации/поиска по названию
    private  fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<CountryData>() //  ArrayList<CountriesAndInfoData>()
            for (i in mList) { // for (i in infoList)
                if (i.country.lowercase().contains(query)) { //  i.mainCountry.name.lowercase().contains(query)
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Country not found", Toast.LENGTH_SHORT).show()
            } else {
                countryAdapter.setFilteredList(filteredList)
            }
        }
    }

}
