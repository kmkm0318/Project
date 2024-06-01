package com.example.project.Function

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.tasks.await

class FindBuilding{
    private var buildingInfoCache: Map<String, Any>? = null
    fun buildingSearch(search: String, cameraPositionState: CameraPositionState){

        val db = Firebase.firestore

        if (buildingInfoCache == null) {
            db.collection("BuildingInfo")
                .get()
                .addOnSuccessListener { result ->
                    val tempCache = mutableMapOf<String, Any>()

                    for (document in result) {
                        val buildinginfo = document.data
                        tempCache.putAll(buildinginfo)
                    }

                    buildingInfoCache = tempCache

                    processSearch(search, cameraPositionState, tempCache)
                }
                .addOnFailureListener { exception ->
                    Log.w("db", "Error getting documents.", exception)
                }
        } else {
            processSearch(search, cameraPositionState, buildingInfoCache!!)
        }

    }
    private fun processSearch(
        search: String,
        cameraPositionState: CameraPositionState,
        buildingInfo: Map<String, Any>
    ) {
        val searchBuildingResult = buildingInfo.keys.filter { it.contains(search) }
//        val searchRoomResult = buildingInfo.values.filter{ it. }

        val building = buildingInfo[search] as? List<Any> ?: return
        val buildingLatLng = building[0] as GeoPoint
        val roomNumbers = building.subList(1, building.size - 1).map { it.toString() }
        val latLng = LatLng(buildingLatLng.latitude, buildingLatLng.longitude)
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))

    }

}
