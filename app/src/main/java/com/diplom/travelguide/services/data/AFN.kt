package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class AFN (

  @SerializedName("name"   ) var name   : String? = null,
  @SerializedName("symbol" ) var symbol : String? = null

)