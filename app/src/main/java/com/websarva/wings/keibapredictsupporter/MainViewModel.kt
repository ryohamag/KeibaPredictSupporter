package com.websarva.wings.keibapredictsupporter

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.websarva.wings.keibapredictsupporter.DataClass.HorseData
import com.websarva.wings.keibapredictsupporter.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel(private val context: Context): ViewModel() {
    val calendar = Calendar.getInstance()
    var year = mutableStateOf(calendar.get(Calendar.YEAR).toString()) //入力された年
    var racecourse = mutableStateOf("05") //入力された競馬場
    var round = mutableStateOf("1") //第n回
    var day = mutableStateOf("1") //第n日
    var numOfRace = mutableStateOf("1") //何レース目


    private val _shutubaData = MutableLiveData<List<HorseData>>()
    val shutubaData: LiveData<List<HorseData>>
        get() = _shutubaData

    private val apiClient = ApiClient()

    fun fetchShutubaData() {
        val formattedRound = String.format("%02d", round.value.toInt())
        val formattedDay = String.format("%02d", day.value.toInt())
        val formattedNumOfRace = String.format("%02d", numOfRace.value.toInt())
        val filename = "${year.value}${racecourse.value}${formattedRound}${formattedDay}${formattedNumOfRace}.json"

        viewModelScope.launch {
            val response = apiClient.fetchShutubaData(filename)
            val horseDataList = response.body()

            if (horseDataList != null) {
                _shutubaData.value = horseDataList!!
                val gson = Gson()
                val jsonData = gson.toJson(horseDataList) // JSONフォーマットに変換
                saveJsonToFile(context, filename, jsonData)
                Log.d("MainViewModel", "fetchShutubaData: $jsonData")
            } else {
                Log.e("MainViewModel", "fetchShutubaData: response body is null")
            }
        }
    }

    private fun saveJsonToFile(context: Context, fileName: String, jsonData: String) {
        val file = File(context.filesDir, fileName)  // 内部ストレージのファイルパス
        file.writeText(jsonData)  // JSONデータを書き込む
        Log.d("MainViewModel", "saveJsonToFile: $jsonData")
    }

    fun readJsonFromFile(fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                Log.d("MainViewModel", "readJsonFromFile: File found")
                try {
                    val jsonString = file.readText()
                    val gson = Gson()
                    val type = object : TypeToken<List<HorseData>>() {}.type
                    val horseDataList: List<HorseData> = gson.fromJson(jsonString, type)
                    withContext(Dispatchers.Main) {
                        _shutubaData.value = horseDataList
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        _shutubaData.value = emptyList()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.d("MainViewModel", "readJsonFromFile: File not found")
                    _shutubaData.value = emptyList()
                }
            }
        }
    }

    fun deleteJsonFile(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.delete()
    }
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}