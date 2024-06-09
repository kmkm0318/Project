package com.example.project.Function

import android.util.Log
import com.example.project.Class.NavViewModel
import com.google.android.gms.maps.model.LatLng

fun ShowFriendOnMap(viewModel: NavViewModel,friendName:String, lat:Double, lng:Double) {
    Log.i("showFriendOnMap1", "$friendName lat : $lat , lng : $lng")
    Log.i("showFriendOnMap2", "${viewModel.userData.transactingfriendname} lat : ${viewModel.userData.friendLocationLat} , lng : ${viewModel.userData.friendLocationLng}")
}