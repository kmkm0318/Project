package com.example.project.Class

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.type.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Building(val rusult: String, val latitude: Double, val longitude: Double)
class SearchViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _searchResults = MutableStateFlow<List<Building>>(emptyList())
    val searchResults: StateFlow<List<Building>> = _searchResults

    fun searchBuilding(search: String) {
        viewModelScope.launch {
            db.collection("BuildingInfo")
                .get()
                .addOnSuccessListener { result ->
                    val tempCache = mutableMapOf<String, Any>()
                    val results = mutableListOf<Building>()

                    for (document in result) {
                        val buildinginfo = document.data
                        tempCache.putAll(buildinginfo)
                    }

                    for ((buildingName, details) in tempCache) {
                        if (details is List<*>) {
                            val rooms = details.subList(1, details.size - 2)
                            val buildings = details.subList(details.size - 2, details.size)
                            val latLng = details[0] as? GeoPoint
                            for (room in rooms) {
                                if (room is String && room.contains(search)) {
                                    results.add(Building("$buildingName $room",latLng!!.latitude,latLng.longitude))
                                }
                            }
                            for (building in buildings) {
                                if (building is String && building.contains(search,ignoreCase = true)) {
                                    results.add(Building(buildingName,latLng!!.latitude,latLng.longitude))
                                }
                            }

                        }
                    }
                    _searchResults.value = results
                }
                .addOnFailureListener { exception ->
                    Log.w("db", "Error getting documents.", exception)
                }
        }
    }

}