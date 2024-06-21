package com.diplom.travelguide.ui.adapters.data

import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("name")
    val city: String,
    val info: String?
):java.io.Serializable
