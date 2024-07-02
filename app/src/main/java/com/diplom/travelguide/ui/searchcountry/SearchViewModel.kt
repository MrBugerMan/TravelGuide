package com.diplom.travelguide.ui.searchcountry

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.travelguide.services.SecondApiService
import com.diplom.travelguide.ui.adapters.data.CountryData
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val _recycleViewLiveData = MutableLiveData<ArrayList<CountryData>>()
    val recycleViewLiveData: LiveData<ArrayList<CountryData>>
        get() = _recycleViewLiveData

    init {
        viewModelScope.launch {
            getCountries()
        }
    }

    // функция получения данных стран из API
    private suspend fun getCountries() {
        val response = SecondApiService.retrofitNewService.getCountries()
        _recycleViewLiveData.value = response
    }


    fun filterList(query: String?) {
        if (query != null) {
            val currentList = recycleViewLiveData.value ?: return
            val filteredList = ArrayList<CountryData>()
            for (i in currentList) {
                if (i.name.lowercase().contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Log.d("Error - getCity", "Country not found")
            } else {
                _recycleViewLiveData.value = filteredList
            }
        }
    }


}