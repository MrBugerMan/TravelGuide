package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class NativeNames (

    @SerializedName("prs" ) var prs : Prs? = Prs(),
    @SerializedName("pus" ) var pus : Pus? = Pus(),
    @SerializedName("tuk" ) var tuk : Tuk? = Tuk()

)