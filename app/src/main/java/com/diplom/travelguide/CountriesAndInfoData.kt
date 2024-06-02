package com.diplom.travelguide


data class CountriesAndInfoData (
    val mainCountry: CountryInfo,
):java.io.Serializable
data class CountryAndCapital (
    val country: ArrayList<Double>,
    val capital: ArrayList<Double>,
):java.io.Serializable
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


/*
data class CountriesAndInfoData (
    val mainCountry: JsonToKotlinBaseAt,
):java.io.Serializable
data class JsonToKotlinBaseLatLng (
    val country: Array<Double>,
    val capital: Array<Double>,
):java.io.Serializable
data class JsonToKotlinBaseEng (
    val f: String,
    val m: String,
):java.io.Serializable
data class JsonToKotlinBaseFra (
    val f: String,
    val m: String,
):java.io.Serializable
data class JsonToKotlinBaseDemonyms (
    val eng: JsonToKotlinBaseEng,
    val fra: JsonToKotlinBaseFra,
):java.io.Serializable

data class JsonToKotlinBaseBar (
    val official: String,
    val common: String,
):java.io.Serializable
data class JsonToKotlinBaseNativeNames (
    val bar: JsonToKotlinBaseBar,
):java.io.Serializable
data class JsonToKotlinBaseEUR (
    val name: String,
    val symbol: Char,
):java.io.Serializable
data class JsonToKotlinBaseCurrencies (
    val EUR: JsonToKotlinBaseEUR,
):java.io.Serializable
data class JsonToKotlinBaseLanguages (
    val bar: String,
):java.io.Serializable
data class JsonToKotlinBaseTranslations (
    val ara: String,
    val ces: String,
    val cym: String,
    val deu: String,
    val est: String,
    val fin: String,
    val fra: String,
    val hrv: String,
    val hun: String,
    val ita: String,
    val jpn: String,
    val kor: String,
    val nld: String,
    val per: String,
    val pol: String,
    val por: String,
    val rus: String,
    val slk: String,
    val spa: String,
    val swe: String,
    val urd: String,
    val zho: String,
):java.io.Serializable
data class JsonToKotlinBaseFlag (
    val small: String,
    val medium: String,
    val large: String,
):java.io.Serializable
data class JsonToKotlinBaseAt (
    val name: String,
    val official_name: String,
    val topLevelDomain: Array<Any>,
    val alpha2Code: String,
    val alpha3Code: String,
    val cioc: String,
    val numericCode: String,
    val callingCode: String,
    val capital: String,
    val altSpellings: Array<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val latLng: JsonToKotlinBaseLatLng,
    val demonyms: JsonToKotlinBaseDemonyms,
    val area: Int,
    val timezones: Array<Any>,
    val borders: Array<String>,
    val nativeNames: JsonToKotlinBaseNativeNames,
    val currencies: JsonToKotlinBaseCurrencies,
    val languages: JsonToKotlinBaseLanguages,
    val translations: JsonToKotlinBaseTranslations,
    val flag: JsonToKotlinBaseFlag,
    val regionalBlocs: Array<Any>,
):java.io.Serializable*/
