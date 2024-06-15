package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class RegionalBlocs (

  @SerializedName("acronym" ) var acronym : String? = null,
  @SerializedName("name"    ) var name    : String? = null

)