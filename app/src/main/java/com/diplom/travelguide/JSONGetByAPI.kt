package com.diplom.travelguide

import com.diplom.travelguide.countries.CountryData
import com.diplom.travelguide.countrydetails.CityData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL: String = "https://api.countrystatecity.in/"
private const val API_KEY: String = "S0tSUGVQQWdiMEZZQVdsMHBZQ1pBbzZjWVFYUkk2R0ZROWJBUWtwRA==" // ключ на почте

private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
    BASE_URL
).build()

interface ServiceAPI {
    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries")
    fun getCountries(): Call<ArrayList<CountryData>>

    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries/{countryCode}/cities")
    fun getCities(@Path("countryCode") countryCode: String): Call<ArrayList<CityData>>


}

object ApiService {
    val retrofitService: ServiceAPI by lazy{ retrofit.create(ServiceAPI::class.java)}
}