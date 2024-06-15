package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class Tuk (

  @SerializedName("official" ) var official : String? = null,
  @SerializedName("common"   ) var common   : String? = null

)