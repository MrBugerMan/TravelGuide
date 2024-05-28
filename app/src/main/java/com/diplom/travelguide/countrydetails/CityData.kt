package com.diplom.travelguide.countrydetails

import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("name")
    val city: String
):java.io.Serializable
