package com.diplom.travelguide.services.data

import com.google.gson.annotations.SerializedName


data class CountryInfo (

    @SerializedName("name"           ) var name           : String?                  = null,
    @SerializedName("official_name"  ) var officialName   : String?                  = null,
    @SerializedName("topLevelDomain" ) var topLevelDomain : ArrayList<String>        = arrayListOf(),
    @SerializedName("alpha2Code"     ) var alpha2Code     : String?                  = null,
    @SerializedName("alpha3Code"     ) var alpha3Code     : String?                  = null,
    @SerializedName("cioc"           ) var cioc           : String?                  = null,
    @SerializedName("numericCode"    ) var numericCode    : String?                  = null,
    @SerializedName("callingCode"    ) var callingCode    : String?                  = null,
    @SerializedName("capital"        ) var capital        : String?                  = null,
    @SerializedName("altSpellings"   ) var altSpellings   : ArrayList<String>        = arrayListOf(),
    @SerializedName("region"         ) var region         : String?                  = null,
    @SerializedName("subregion"      ) var subregion      : String?                  = null,
    @SerializedName("population"     ) var population     : Int?                     = null,
    @SerializedName("latLng"         ) var latLng         : LatLng?                  = LatLng(),
    @SerializedName("demonyms"       ) var demonyms       : Demonyms?                = Demonyms(),
    @SerializedName("area"           ) var area           : Double?                  = null,
  //@SerializedName("gini"           ) var gini           : String?                  = null,          //  удалить везде и в json
    @SerializedName("timezones"      ) var timezones      : ArrayList<String>        = arrayListOf(),
  //@SerializedName("borders"        ) var borders        : ArrayList<String>        = arrayListOf(), // иногда там не список а строка пока сотру
  //@SerializedName("nativeNames"    ) var nativeNames    : NativeNames?             = NativeNames(), //  удалить везде и в json
  //@SerializedName("currencies"     ) var currencies     : Currencies?              = Currencies(),  //  удалить везде и в json
  //@SerializedName("languages"      ) var languages      : Languages?               = Languages(),   //  удалить везде и в json
    @SerializedName("translations"   ) var translations   : Translations?            = Translations(),
    @SerializedName("flag"           ) var flag           : Flag?                    = Flag(),
    @SerializedName("regionalBlocs"  ) var regionalBlocs  : ArrayList<RegionalBlocs> = arrayListOf()  // под вопросом, т.к. нет "otherNames"

)