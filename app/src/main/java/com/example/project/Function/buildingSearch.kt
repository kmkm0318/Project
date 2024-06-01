package com.example.project.Function

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore
import com.google.maps.android.compose.CameraPositionState



fun buildingSearch(search: String, cameraPositionState: CameraPositionState){
    val db = Firebase.firestore
    db.collection("BuildingInfo")
        .get()
        .addOnSuccessListener {result ->
            for (document in result){
                val buildinginfo = document.data
                val building = buildinginfo["$search"] as? List<Any> ?: continue
                val buildingArray = building as? List<Any> ?: continue
                val buildingLatLng = buildingArray[0] as GeoPoint
                val roomNumbers = buildingArray.subList(1, buildingArray.size).map { it.toString() }


                val latLng = LatLng(buildingLatLng.latitude,buildingLatLng.longitude)
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))

            }
        }
        .addOnFailureListener {exception ->
            Log.w("db", "Error getting documents.", exception)
        }
}