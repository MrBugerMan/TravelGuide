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
private val retrofitFirst = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
    BASE_URL
).build()



private const val BIG_API_KEY: String = "lCGEor1fePaRl2G98a3Lg3KV5NZ4odt4w79s2R1u"
private const val BIG_BASE_URL: String = "https://countryapi.io/api/"
private val retrofitSecond = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
    BIG_BASE_URL
).build()

interface ServiceAPI {
    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries")
    fun getCountries(): Call<ArrayList<CountryData>>

    @Headers("X-CSCAPI-KEY: $API_KEY")
    @GET("v1/countries/{countryCode}/cities")
    fun getCities(@Path("countryCode") countryCode: String): Call<ArrayList<CityData>>


    @Headers("Authorization: Bearer $BIG_API_KEY")
    @GET("name/{name}")
    fun getAllCountriesAndInfo(@Path("name") name: String): Call<ArrayList<CountriesAndInfoData>>


}

object ApiService {
    val retrofitService: ServiceAPI by lazy{ retrofitFirst.create(ServiceAPI::class.java)}
    val retrofitServiceSecond: ServiceAPI by lazy{ retrofitSecond.create(ServiceAPI::class.java)}
}


