package com.diplom.travelguide.services

import com.diplom.travelguide.adapters.data.CityData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL: String = "https://api.countrystatecity.in/"
private const val API_KEY: String = "S0tSUGVQQWdiMEZZQVdsMHBZQ1pBbzZjWVFYUkk2R0ZROWJBUWtwRA==" // ключ на почте
private val retrofitNew = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
interface SecondServiceInterface{

    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries/{countryCode}/cities")
    suspend fun getCities2(@Path("countryCode") countryCode: String): ArrayList<CityData>
}

object SecondApiService {
    val retrofitNewService: SecondServiceInterface by lazy { retrofitNew.create(SecondServiceInterface::class.java) }
}
