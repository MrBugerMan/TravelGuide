package com.diplom.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.diplom.travelguide.databinding.ActivityMainBinding
import retrofit2.Retrofit

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

        val mList = Country.getCountryData()
        recyclerView = binding.recyclerViewCountry
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(mList)
        recyclerView.adapter = countryAdapter

        countryAdapter.setOnClickListener(object:
            CountryAdapter.OnClickListener {
            override fun onClick(position: Int, title: String) {
                val intent = Intent(this@MainActivity, CountryDetails::class.java)
                intent.putExtra(NEXT_SCREEN, title)
                startActivity(intent)
            }
        })

        //addToDataList() // заполнение массива перед передачей в адаптер (старая версия)
    }
    companion object{
        const val NEXT_SCREEN = "details_screen"}



    // функция фильтрации/поиска по названию
    private  fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<CountryData>()
            for (i in mList) {
                if (i.title.lowercase().contains(query) ) {
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

    /*// через эту функцию надо заполнить массив Названием стран и их флагами
    private fun addToDataList() {
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
    }*/


    /*private fun getNameCity(mList: List<CountryData>){
        val url = "https://api.countrystatecity.in/v1/countries"
        val request = Retrofit.Builder()
    }*/

}