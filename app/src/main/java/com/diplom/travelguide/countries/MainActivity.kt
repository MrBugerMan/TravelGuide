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
                CountryViewModel().filterList(newText, mList, countryAdapter)
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
        CountryViewModel().getCountries(mList, countryAdapter)





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


    companion object{
        const val COUNTRY_ACTIVITY = "details_screen"}

}
