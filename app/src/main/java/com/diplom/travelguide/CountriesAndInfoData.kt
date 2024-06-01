package com.diplom.travelguide

/*data class CountriesAndInfoData(
    val c: String
):java.io.Serializable*/

data class CountriesAndInfoData (
    val mainCountry: CountryInfo,
):java.io.Serializable
data class CountryAndCapital (
    val country: ArrayList<Double>,
    val capital: ArrayList<Double>,
)
data class CountyLanguage (
    val prs: String,
    val pus: String,
    val tuk: String,
):java.io.Serializable
data class CountryInfo (
    val name: String,
    val alpha2Code: String,
    val callingCode: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Int,
    val latLng: CountryAndCapital,
    val timezones: ArrayList<Any>,
    val languages: CountyLanguage,
):java.io.Serializable
