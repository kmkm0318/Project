package com.example.project.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsTransitFilled
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.Function.RequestLocationPermission
import com.example.project.Function.buildingSearch
import com.example.project.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen() {
    var search by remember{ mutableStateOf("") }
    val context = LocalContext.current
    val locationClient = remember{ LocationServices.getFusedLocationProviderClient(context) }
    var locationNow = remember{ mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(locationNow.value?: LatLng(37.5424219288, 127.076761802), 18f)}

    RequestLocationPermission(
        onPermissionGranted = {
            try{
                locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener { locationResult ->
                    locationResult?.let { location ->
                        locationNow.value = LatLng(location.latitude, location.longitude)
                    }
                }
            }catch(e: SecurityException){
                e.printStackTrace()
            }
        }
    )

    Box(modifier = Modifier
        .fillMaxWidth(),

        ){
        LaunchedEffect(Unit){
            getLastKnownLocation(locationClient) { latLng ->
                locationNow.value = latLng
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
            }
        }
        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = false,
                myLocationButtonEnabled = true
            )
        )
        OutlinedTextField(
            value = search,
            onValueChange = {search = it},
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp)
                .border(
                    3.dp,
                    color = colorResource(id = R.color.kudarkgreen),
                    shape = RoundedCornerShape(12)
                )
                .fillMaxWidth(),
            placeholder = { Text(text="건물/강의실 검색", fontSize = 16.sp) },
            trailingIcon = {
                IconButton(onClick = {
                    //검색기능
                    buildingSearch(search, cameraPositionState)
                }) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 6.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.kudarkgreen),
                    )
                }
            },
            singleLine = true
        )

    }

    Column(modifier= Modifier
        .fillMaxSize()
        .padding(12.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        FloatingActionButton( //현위치로 이동
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp),
            shape = RoundedCornerShape(100),
            containerColor = colorResource(id = R.color.kulightgreen),
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = {
                getLastKnownLocation(locationClient) { latLng ->
                    locationNow.value = latLng
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                }
            }
        ) {
            Icon(imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = colorResource(id = R.color.kudarkgreen)
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp),
            shape = RoundedCornerShape(100),
            containerColor = colorResource(id = R.color.kulightgreen),
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = {
                /*근처 대중교통 시간표 검색*/
            }) {
            Icon(imageVector = Icons.Default.DirectionsTransitFilled,
                contentDescription = null,
                tint = colorResource(id = R.color.kudarkgreen)
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp),
            shape = RoundedCornerShape(100),
            containerColor = colorResource(id = R.color.kulightgreen),
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            onClick = {
                /*인스타그램,카카오톡,위치공유,기타앱공유*/
            }) {
            Icon(imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = colorResource(id = R.color.kudarkgreen)
            )
        }
    }
}

fun getLastKnownLocation(fusedLocationProviderClient: FusedLocationProviderClient, onLocationReceived: (LatLng) -> Unit) {
    try {
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener { locationResult ->
            locationResult?.let {
                onLocationReceived(LatLng(it.latitude, it.longitude))
            }
        }
    } catch (e: SecurityException) {
        e.printStackTrace()  // 예외 처리
    }
}