package com.example.project.Screen

import Routes
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.NavViewModel
import com.example.project.Function.FindBuilding
import com.example.project.Function.RequestLocationPermission
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
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
import kotlinx.coroutines.delay


@Composable
fun MapScreen(navController: NavController) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var search by remember{ mutableStateOf("") }
    val context = LocalContext.current
    val locationClient = remember{ LocationServices.getFusedLocationProviderClient(context) }
    var locationNow = remember{ mutableStateOf<LatLng?>(null) }
    val focusManager = LocalFocusManager.current
    var followCurrentLocation by remember{ mutableStateOf(true) }
    val findBuilding = remember { FindBuilding() }

    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(locationNow.value?: LatLng(37.5424219288, 127.076761802), 18f)}

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.kumiddlegreen),
        unfocusedBorderColor = colorResource(id = R.color.kudarkgreen),
        cursorColor = colorResource(id = R.color.kudarkgreen),
        focusedTextColor = colorResource(id = R.color.kudarkgreen),
        unfocusedTextColor = colorResource(id = R.color.kumiddlegreen),
        focusedContainerColor = colorResource(id = R.color.kuhighlightgreen),
        unfocusedContainerColor = colorResource(id = R.color.kuhighlightgreen)
    )

    locationNow.value?.let {
        navViewModel.userData.locationNowLat = it.latitude
        navViewModel.userData.locationNowLng = it.longitude
    }

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

    fun clearText(){
        search = ""
    }

    Box(modifier = Modifier
        .fillMaxWidth(),
    ){
        LaunchedEffect(followCurrentLocation){
            while(followCurrentLocation){
                getLastKnownLocation(locationClient) { latLng ->
                    locationNow.value = latLng
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                }
                delay(1000L)
            }
        }
        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = false,
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false
            )
        )
        OutlinedTextField(
            value = search,
            textStyle = TextStyle(colorResource(id = R.color.kudarkgreen),
                fontFamily=fontFamily,
                fontSize = 20.sp),
            onValueChange = {search = it},
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp)
                .border(
                    3.dp,
                    color = colorResource(id = R.color.kudarkgreen),
                    shape = RoundedCornerShape(12)
                )
                .fillMaxWidth(),
            colors = textFieldColors,
            placeholder = { Text(text="찾고 싶은 강의실을 검색하세요!", fontFamily=fontFamily, fontSize = 20.sp) },
            trailingIcon = {
                IconButton(onClick = {
                    //검색기능
                    findBuilding.buildingSearch(search,cameraPositionState)
                    focusManager.clearFocus()
                    clearText()
                    followCurrentLocation = false
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
                    followCurrentLocation = true
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
                navController.navigate(Routes.Metro.route) {
                    popUpTo(Routes.Map.route) {
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
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
                onClickShareButton(navController)
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

fun onClickShareButton(navController: NavController) {
    navController.navigate(Routes.Friend.route)
}