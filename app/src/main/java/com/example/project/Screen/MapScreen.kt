package com.example.project.Screen

import Routes
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.DirectionsTransitFilled
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TravelExplore
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.NavViewModel
import com.example.project.Class.SearchViewModel
import com.example.project.Compose.SearchRecommend
import com.example.project.Function.FindBuilding
import com.example.project.Function.RequestLocationPermission
import com.example.project.Function.ShowFriendOnMap
import com.example.project.Function.getCharacterImageAsInt
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.play.integrity.internal.s
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MapScreen(navController: NavController) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    var search by remember{ mutableStateOf("") }
    val searchViewModel = SearchViewModel()
    val context = LocalContext.current
    val locationClient = remember{ LocationServices.getFusedLocationProviderClient(context) }
    var locationNow = remember{ mutableStateOf<LatLng?>(null) }
    val focusManager = LocalFocusManager.current
    var trackingEnabled by remember { mutableStateOf(true) }
    val findBuilding = remember { FindBuilding() }

    var openDialog by remember{mutableStateOf(false)}

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

    Box(modifier = Modifier
        .fillMaxWidth(),
    ){
        LaunchedEffect(trackingEnabled){
            while(trackingEnabled){
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
        ){
            if(navViewModel.receiveFriendLocation.value){
                val transactingFriend = navViewModel.userData.friendList!!.filter{it.studentID == navViewModel.userData.transactingfriendname}
                val tFriendCharacter = getCharacterImageAsInt(context,transactingFriend[0].characterData)
                MarkerInfoWindow(
                    state = MarkerState(position = LatLng(navViewModel.userData.friendLocationLat,navViewModel.userData.friendLocationLng)),
                    title = navViewModel.userData.transactingfriendname,
                    tag = transactingFriend[0].name,
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.markerimage)
                ){
                    Box(modifier = Modifier.width(140.dp)
                        .height(140.dp)
                        .background(colorResource(id = R.color.kuhighlightgreen))
                        .border(3.dp, colorResource(id = R.color.kumiddlegreen),
                            RoundedCornerShape(10.dp)
                        )
                        ,contentAlignment = Alignment.Center){
                        Column(verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            Text(transactingFriend[0].name, fontFamily = fontFamily,
                                fontSize = 16.sp, color = colorResource(id = R.color.kudarkgreen))
                            Image(painter = painterResource(id = tFriendCharacter),"",
                                modifier = Modifier.size(80.dp))
                        }
                    }


                }
                LaunchedEffect(Unit){
                    Log.i("showFriendOnMap3", "${navViewModel.userData.transactingfriendname} lat : ${navViewModel.userData.friendLocationLat} , lng : ${navViewModel.userData.friendLocationLng}")
                    delay(5000L)
                    navViewModel.receiveFriendLocation.value = false
                }
            }

        }
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = search,
                textStyle = TextStyle(colorResource(id = R.color.kudarkgreen),
                    fontFamily=fontFamily,
                    fontSize = 20.sp),
                onValueChange = { search = it },
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .height(60.dp)
                    .border(
                        3.dp,
                        color = colorResource(id = R.color.kudarkgreen),
                        shape = RoundedCornerShape(12)
                    )
                    .fillMaxWidth(),
                colors = textFieldColors,
                placeholder = {
                    if(navViewModel.language.value == "kr"){
                        Text(text="찾고 싶은 강의실을 검색하세요!", fontFamily=fontFamily, fontSize = 20.sp)
                    }
                    else{
                        Text(text="Search for Buildings or Lecture Rooms!", fontFamily=fontFamily, fontSize = 16.sp)
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        //검색기능
                        findBuilding.buildingSearch(search,cameraPositionState)
                        focusManager.clearFocus()
                        search = ""
                        trackingEnabled = false
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
            if(search != ""){
                SearchRecommend(search,
                    searchViewModel,
                    cameraPositionState,
                    onSearchCleared = {
                        search = ""
                        trackingEnabled=false
                        focusManager.clearFocus()
                    }
                )
            }
        }
        if(trackingEnabled){
            Box(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)){
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)) {
                    drawCircle(
                        color = Color(160,220,100),
                        radius = 20f,
                        center = Offset(size.width*0.5f,size.height*0.5f)
                    )
                }
            }
        }
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
                trackingEnabled=true
                getLastKnownLocation(locationClient) { latLng ->
                    locationNow.value = latLng
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                    Log.w("latlng1","$latLng")
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
                //openDialog = true
                //onClickShareButton(navController)
                navController.navigate(Routes.Friend.route) {
                    popUpTo(Routes.Map.route) {
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
            Icon(imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = colorResource(id = R.color.kudarkgreen)
            )
        }
        if(trackingEnabled){
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp),
                shape = RoundedCornerShape(100),
                containerColor = colorResource(id = R.color.kulightgreen),
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                onClick = {
                    /*tracking 중지버튼*/
                    trackingEnabled = false
                }) {
                Icon(painter = painterResource(id = R.drawable.baseline_touch_app),
                    contentDescription = null,
                    tint = colorResource(id = R.color.kudarkgreen)
                )
            }
        }
        else{
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp),
                shape = RoundedCornerShape(100),
                containerColor = colorResource(id = R.color.white),
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                onClick = {
                    /*tracking 재개버튼*/
                    trackingEnabled=true
                    getLastKnownLocation(locationClient) { latLng ->
                        locationNow.value = latLng
                        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                        Log.w("latlng2","$latLng")
                    }
                }) {
                Icon(imageVector = Icons.Default.MyLocation,
                    contentDescription = null,
                    tint = colorResource(id = R.color.kudarkgreen)
                )
            }
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