package com.diplom.travelguide


object Country {
    fun getCountryData(): ArrayList<CountryData> {
        val mList = ArrayList<CountryData>()
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Russia", R.drawable.ru))
        mList.add(CountryData("Japan", R.drawable.jp))
        mList.add(CountryData("Russia", R.drawable.ru))

        return mList
    }
}