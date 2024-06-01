package com.diplom.travelguide.countrydetails

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.diplom.travelguide.ApiService
import retrofit2.Call
import retrofit2.Response

class CountryDetailsViewModel {

    fun getCities(iso2: String, cityList: ArrayList<CityData>, cityAdapter: CityAdapter) {
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
                    Toast.makeText(CountryDetails(), "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CityData>>, t: Throwable) {
                Log.d("Error - getCity", t.message.toString())
            }

        })
    }

    fun filterList(query: String?, cityList: ArrayList<CityData>, cityAdapter: CityAdapter) {
        if (query != null) {
            val filteredList = ArrayList<CityData>() //  ArrayList<CountriesAndInfoData>()
            for (i in cityList) { // for (i in infoList)
                if (i.city.lowercase().contains(query)) { //  i.mainCountry.name.lowercase().contains(query)
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(CountryDetails(), "City not found", Toast.LENGTH_SHORT).show()
            } else {
                cityAdapter.setFilteredList(filteredList)
            }
        }
    }


}