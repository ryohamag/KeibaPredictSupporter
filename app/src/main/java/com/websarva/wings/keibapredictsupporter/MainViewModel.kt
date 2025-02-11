package com.websarva.wings.keibapredictsupporter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.keibapredictsupporter.DataClass.HorseData
import com.websarva.wings.keibapredictsupporter.network.ApiClient
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _shutubaData = MutableLiveData<List<HorseData>>()
    val shutubaData: LiveData<List<HorseData>>
        get() = _shutubaData

    private val apiClient = ApiClient()

    fun fetchShutubaData(filename: String) {
        viewModelScope.launch {
            val response = apiClient.fetchShutubaData(filename)
            _shutubaData.value = response.body()
            Log.d("MainViewModel", "fetchShutubaData: ${response.body()}")
        }
    }
}