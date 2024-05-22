package com.diplom.travelguide.countries

import com.google.gson.annotations.SerializedName


//data class CountryData(val country: String, val flag: String):java.io.Serializable
data class CountryData(
    val id: Int,
    @SerializedName("name")
    val country: String,
    val iso2: String
):java.io.Serializable
