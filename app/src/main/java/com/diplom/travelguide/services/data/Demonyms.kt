package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class Demonyms (

    @SerializedName("eng" ) var eng : Eng? = Eng(),
    @SerializedName("fra" ) var fra : Fra? = Fra()

)