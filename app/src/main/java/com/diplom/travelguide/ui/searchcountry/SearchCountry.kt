package com.diplom.travelguide.ui.searchcountry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplom.travelguide.R
import com.diplom.travelguide.adapters.CountryAdapter
import com.diplom.travelguide.adapters.data.CountryData
import com.diplom.travelguide.countries.CountryViewModel
import com.diplom.travelguide.databinding.ActivityMainBinding
import com.diplom.travelguide.services.data.CountryInfo
import com.diplom.travelguide.ui.countrydetails.CountryDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yandex.mapkit.MapKitFactory
import java.io.InputStreamReader

class SearchCountry : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<CountryData>()
    private lateinit var countryAdapter: CountryAdapter

    //private var mList = ArrayList<CountriesAndInfoData>()
    //private var countryInfo = ArrayList<CountriesAndInfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.fragment_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(view)
        }, 3000)

        // устанавливаем ключь для API Яндекс.Карт
        MapKitFactory.setApiKey("d1d42e49-be29-4221-b7a0-d7888f5ee8af")


        // поиск
        searchView = binding.searchCountry
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CountryViewModel().filterList(newText, mList, countryAdapter) // , countryInfo,
                return true
            }

        })

        // настройка RecyclerView
        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(mList) // countryInfo
        //countryAdapter = CountryAdapter(infoList)
        recyclerView.adapter = countryAdapter

        // получаем данные из API (список стран и их индификатор для скачивания картинок))
        CountryViewModel().getCountries(mList)

        //CountryViewModel().getCountriesInfo(this)

        /*lifecycleScope.launch {
            getCountriesInfo()
        }*/

        val countryInfoList = readCountriesFromAssets(this@SearchCountry)
        binding.test.text = countryInfoList?.get(0)?.name ?: "No data"

        /*lifecycleScope.launch {
            //getCountriesInfo1()

        }*/

        //CountryViewModel().getAllCountriesAndInfo(countryInfo, mList, countryAdapter)
        //countryAdapter.notifyDataSetChanged() // countryAdapter = CountryAdapter(mList) // и так и так не работает



///////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!///////////////////////////////////
        // передача данных в новое активити и переход в новое активити при нажатии на элемент RecyclerView
        countryAdapter.setOnClickListener(object:
            CountryAdapter.OnClickListener {
            override fun onClick(position: Int, model: CountryData) { // model: CountriesAndInfoData
                val intent = Intent(this@SearchCountry, CountryDetails::class.java)
                intent.putExtra(COUNTRY_ACTIVITY, model)
                startActivity(intent)
            }
        })

    }
///////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!///////////////////////////////////

    /*private suspend fun getCountriesInfo() {
        val jsonString = withContext(Dispatchers.IO) {
            try {
                assets.open("countries.json").bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                Log.e("MyDataLog", "Error reading JSON file", e)
                return@withContext ""
            }
        }

        val gson = Gson()
        val data = withContext(Dispatchers.IO) {
            try {
                gson.fromJson(jsonString, Array<CountriesAndInfoData>::class.java)
            } catch (e: Exception) {
                Log.e("MyDataLog", "Error parsing JSON", e)
                return@withContext null
            }
        }

        if (!data.isNullOrEmpty()) {
            Log.d("MyDataLog", data[0].mainCountry.name.toString())
        } else {
            Log.e("MyDataLog", "Data is null or empty, or mainCountry is null")
        }
    }*/

    /*private suspend fun getCountriesInfo1() {
        val jsonString = withContext(Dispatchers.IO) {
            try {
                assets.open("countries1.json").bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                Log.e("MyDataLog", "Error reading JSON file", e)
                return@withContext ""
            }
        }

        val gson = Gson()
        val data = withContext(Dispatchers.IO) {
            try {
                gson.fromJson(jsonString, Array<CountryInfo>::class.java)
            } catch (e: Exception) {
                Log.e("MyDataLog", "Error parsing JSON", e)
                return@withContext null
            }
        }

        if (!data.isNullOrEmpty()) {
            Log.d("MyDataLog", data[0].name.toString() + "second var")
        } else {
            Log.e("MyDataLog", "Data is null or empty, or mainCountry is null")
        }
    }*/

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
