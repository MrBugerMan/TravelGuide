package com.diplom.travelguide.countries

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL: String = "https://api.countrystatecity.in/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ServiceAPI { // надо зарегаться и вставить ключ
    @GET("v1/countries")
    fun getCountries(): Call<ArrayList<CountryData>>
}

object Api {
    val retrofitService: ServiceAPI by lazy{retrofit.create(ServiceAPI::class.java)}
}