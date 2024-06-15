package com.diplom.travelguide.countries

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.diplom.travelguide.services.ApiService
import com.diplom.travelguide.adapters.CountryAdapter
import com.diplom.travelguide.adapters.data.CountryData
import com.diplom.travelguide.ui.searchcountry.SearchCountry
import retrofit2.Call
import retrofit2.Response

class CountryViewModel {


    // функция получения данных стран из API
    fun getCountries(mList: ArrayList<CountryData>) {
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
                }
                else{
                    Toast.makeText(SearchCountry(), "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                Log.d("Error - getCountries", t.message.toString())
            }

        })
    }

    // функция фильтрации/поиска по названию
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?, mList: ArrayList<CountryData>, countryAdapter: CountryAdapter) { // mList: ArrayList<CountriesAndInfoData>
        if (query != null) {
            val filteredList = ArrayList<CountryData>() // ArrayList<CountriesAndInfoData>()
            for (i in mList) { // for (i in infoList)
                if (i.name.lowercase().contains(query)) { // i.mainCountry.name.lowercase().contains(query)
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                //Toast.makeText(SearchCountry(), "Country not found", Toast.LENGTH_SHORT).show()
                Log.d("Error - getCity", "Country not found")
            } else {
                countryAdapter.setFilteredList(filteredList)
                countryAdapter.notifyDataSetChanged()
            }
        }
    }


    /*fun getCountriesInfo(context: Context){
        val jsonString = try {
            context.assets.open("countriesdata/countries.json").bufferedReader().use { it.readText() }
        }catch (e: Exception){ "" }
        val gson = Gson()
        val data = gson.fromJson(jsonString, Array<CountriesAndInfoData>::class.java)

        Log.d("MyDataLog", data[0].mainCountry.name)
    }*/






    // попытка использования другого API
    /*fun getAllCountriesAndInfo(countryInfo: ArrayList<CountriesAndInfoData>, nameList: ArrayList<CountryData>, countryAdapter: CountryAdapter){
        Log.d("ERROR!!!!!!!", "anu") // , countryAdapter: CountryAdapter
        for (country in nameList){
            ApiService.retrofitServiceSecond.getAllCountriesAndInfo(country.name).enqueue(object : retrofit2.Callback<ArrayList<CountriesAndInfoData>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<CountriesAndInfoData>>,
                    response: Response<ArrayList<CountriesAndInfoData>>
                )
                {
                    Log.d("ERORR!!!!!!!", response.body().toString())
                    if(response.isSuccessful){
                        // Обновление mList с данными из ответа сервера
                        countryInfo.clear()
                        countryInfo.addAll(response.body() ?: emptyList())


                        // Уведомление адаптера об изменениях
                        countryAdapter.notifyDataSetChanged()
                        Log.d("getAllCountriesAndInfo", "ВСЁ НОРМ ОТРАБОТАЛО")
                    }
                    else{
                        Toast.makeText(SearchCountry(), "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<ArrayList<CountriesAndInfoData>>, t: Throwable) {
                    Log.d("Error - getAllCountriesAndInfo", t.message.toString())
                }

            })
        }

    }*/
    /*
    val arrayu = arrayOf<String>("au", "as" .....)
    .....getAllCountriesAndInfo(arrayu[0]).enque....
    Map <CountryCode, CountryInfo> = HashMap()
    */




}