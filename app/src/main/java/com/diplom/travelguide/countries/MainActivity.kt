package com.diplom.travelguide.countries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplom.travelguide.R
import com.diplom.travelguide.countrydetails.CountryDetails
import com.diplom.travelguide.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<CountryData>()
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

 
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

        //mList = Country.getCountryData()  // хардкод через объект, заполнение списка вручную, flag: Int
        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getCountries()

        countryAdapter = CountryAdapter(mList)
        recyclerView.adapter = countryAdapter

        countryAdapter.setOnClickListener(object:
            CountryAdapter.OnClickListener {
            override fun onClick(position: Int, model: CountryData) {
                val intent = Intent(this@MainActivity, CountryDetails::class.java)
                intent.putExtra(NEXT_SCREEN, model)
                startActivity(intent)
            }
        })

    }

    private fun getCountries() {
        Api.retrofitService.getCountries().enqueue(object : retrofit2.Callback<ArrayList<CountryData>> {
            override fun onResponse(
                call: Call<ArrayList<CountryData>>,
                response: Response<ArrayList<CountryData>>
            ) {
                if(response.isSuccessful){
                    recyclerView = recyclerView.apply{
                        countryAdapter = CountryAdapter(response.body()!!)
                        layoutManager = recyclerView.layoutManager
                        adapter = countryAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    companion object{
        const val NEXT_SCREEN = "details_screen"}



    // функция фильтрации/поиска по названию
    private  fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<CountryData>()
            for (i in mList) {
                if (i.country.lowercase().contains(query) ) {
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


    fun Zaglushkd(){
        TODO()
    }
}