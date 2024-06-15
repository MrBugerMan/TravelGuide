package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class Languages (

  @SerializedName("prs" ) var prs : String? = null,
  @SerializedName("pus" ) var pus : String? = null,
  @SerializedName("tuk" ) var tuk : String? = null

)