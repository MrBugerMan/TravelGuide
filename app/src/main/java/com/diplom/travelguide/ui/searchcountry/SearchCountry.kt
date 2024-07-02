package com.diplom.travelguide.ui.searchcountry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diplom.travelguide.databinding.ActivityMainBinding
import com.diplom.travelguide.services.data.CountryInfo
import com.diplom.travelguide.ui.adapters.CountryAdapter
import com.diplom.travelguide.ui.adapters.data.CountryData
import com.diplom.travelguide.ui.countrydetails.CountryDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yandex.mapkit.MapKitFactory
import java.io.InputStreamReader

class SearchCountry : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var countryAdapter: CountryAdapter

    private val viewModel by viewModels<SearchViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view) // R.layout.fragment_splash_screen

        /*Handler(Looper.getMainLooper()).postDelayed({
            setContentView(view)
        }, 3000)*/

        // устанавливаем ключь для API Яндекс.Карт
        MapKitFactory.setApiKey("d1d42e49-be29-4221-b7a0-d7888f5ee8af")

        initSearch()
        initAdapter(arrayListOf())

        viewModel.recycleViewLiveData.observe(this, Observer { countries ->
            // Обновите ваш RecyclerView или другие компоненты с данными countries
            countryAdapter.updateData(countries)
        })

        // передача данных в новое активити и переход в новое активити при нажатии на элемент RecyclerView
        countryAdapter.setOnClickListener(object:
            CountryAdapter.OnClickListener {
            override fun onClick(position: Int, model: CountryData) { // model: CountriesAndInfoData
                val intent = Intent(this@SearchCountry, CountryDetails::class.java)
                intent.putExtra(COUNTRY_ACTIVITY, model)
                startActivity(intent)
            }
        })


        val countryInfoList = readCountriesFromAssets(this@SearchCountry)
        binding.test.text = countryInfoList?.get(0)?.name ?: "No data"
    }

    private fun initAdapter(list: ArrayList<CountryData>) {
        countryAdapter = CountryAdapter(list)
        binding.recyclerViewCountry.setHasFixedSize(true)
        binding.recyclerViewCountry.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(this@SearchCountry)
        }
    }

    private fun initSearch(){
        searchView = binding.searchCountry
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                /*viewModel.recycleViewLiveData.observe(this@SearchCountry){
                    viewModel.filterList(newText)
                }*/
                viewModel.filterList(newText)
                return true
            }

        })
    }


    private fun readCountriesFromAssets(context: Context): ArrayList<CountryInfo>? { // ArrayList<TestData>?
        val gson = Gson()
        val countryInfoListType = object : TypeToken<ArrayList<CountryInfo>>() {}.type // ArrayList<TestData>?

        /*context.assets.open("countries1.json").use { inputStream ->
            val reader = InputStreamReader(inputStream)
            return gson.fromJson(reader, countryInfoListType)
        }*/
        return try {
            context.assets.open("countries1.json").use { inputStream ->
                val reader = InputStreamReader(inputStream)
                gson.fromJson(reader, countryInfoListType)
            }
        } catch (e: Exception) {
            Log.d("Error - readCountriesFromAssets", e.message.toString())
            println("Error - readCountriesFromAssets: ${e.message.toString()}") // e.printStackTrace()
            null
        }
    }

    companion object{
        const val COUNTRY_ACTIVITY = "details_screen"}


}
