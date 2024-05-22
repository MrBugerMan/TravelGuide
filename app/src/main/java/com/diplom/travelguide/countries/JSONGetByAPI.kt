package com.diplom.travelguide.countries

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL: String = "https://api.countrystatecity.in/"
private const val API_KEY: String = "S0tSUGVQQWdiMEZZQVdsMHBZQ1pBbzZjWVFYUkk2R0ZROWJBUWtwRA==" // ключ на почте

private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()

interface ServiceAPI {
    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries")
    fun getCountries(): Call<ArrayList<CountryData>>
}

object Api {
    val retrofitService: ServiceAPI by lazy{retrofit.create(ServiceAPI::class.java)}
}