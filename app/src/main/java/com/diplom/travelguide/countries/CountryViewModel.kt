package com.diplom.travelguide.countries

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.diplom.travelguide.ApiService
import com.diplom.travelguide.CountriesAndInfoData
import retrofit2.Call
import retrofit2.Response

class CountryViewModel {


    // функция получения данных стран из API
    fun getCountries(mList: ArrayList<CountryData>, countryAdapter: CountryAdapter) {
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
                    Toast.makeText(MainActivity(), "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                Log.d("Error - getCountries", t.message.toString())
            }

        })
    }

    // функция фильтрации/поиска по названию
    fun filterList(query: String?, mList: ArrayList<CountryData>, countryAdapter: CountryAdapter) {
        if (query != null) {
            val filteredList = ArrayList<CountryData>() //  ArrayList<CountriesAndInfoData>()
            for (i in mList) { // for (i in infoList)
                if (i.country.lowercase().contains(query)) { //  i.mainCountry.name.lowercase().contains(query)
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(MainActivity(), "Country not found", Toast.LENGTH_SHORT).show()
            } else {
                countryAdapter.setFilteredList(filteredList)
            }
        }
    }






    // попытка использования другого API
    /*fun getAllCountriesAndInfo(infoList: ArrayList<CountriesAndInfoData>, countryAdapter: CountryAdapter) {
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
                    Toast.makeText(MainActivity(), "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CountriesAndInfoData>>, t: Throwable) {
                Log.d("Error - getAllCountriesAndInfo", t.message.toString())
            }

        })
    }*/





}