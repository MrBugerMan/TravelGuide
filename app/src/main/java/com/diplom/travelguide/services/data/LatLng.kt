package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class LatLng (

  @SerializedName("country" ) var country : ArrayList<Double> = arrayListOf(),
  @SerializedName("capital" ) var capital : ArrayList<Double> = arrayListOf()

)