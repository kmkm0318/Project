package com.example.project.Class

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class StationViewModel : ViewModel() {
    private val _stationList = mutableStateOf<List<StationData>>(emptyList())
    val stationList: MutableState<List<StationData>> = _stationList

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    fun fetchStation(url: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fetchedInfo = getStationInfo(url)
                _stationList.value = fetchedInfo
            } catch (e: Exception) {
                Log.e("error", "fetch 관련 오류 발생", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun getStationInfo(url: String): List<StationData> = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(url).parser(Parser.xmlParser()).get()
        val infoRow = doc.select("row")
        infoRow.map {stationInfo->
            StationData(
                line = stationInfo.select("subwayId").text(),
                upDnLine = stationInfo.select("updnLine").text(),
                trainLineName = stationInfo.select("trainLineNm").text(),
                stationName = stationInfo.select("statnNm").text(),
                arrivalMsg1 = stationInfo.select("arvlMsg2").text(),
                arrivalMsg2 = stationInfo.select("arvlMsg3").text()
            )
        }
    }
}