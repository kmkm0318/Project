package com.example.project.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.project.Class.NavViewModel
import com.example.project.Class.StationViewModel
import com.example.project.Compose.MetroTopBar
import com.example.project.R
import kotlinx.coroutines.launch


@Composable
fun ChildrensGrandPark(navController: NavController, stationViewModel: StationViewModel,navViewModel: NavViewModel) {
    Scaffold(
        topBar = { MetroTopBar(navController = navController) }
    ) {
        ChildrensGrandParkContent(stationViewModel = stationViewModel, contentPadding = it, navViewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChildrensGrandParkContent(stationViewModel: StationViewModel, contentPadding:PaddingValues,navViewModel: NavViewModel) {
    val url = "http://swopenAPI.seoul.go.kr/api/subway/426e4f7346776f6f393577474f4b51/xml/realtimeStationArrival/0/4/어린이대공원(세종대)"
    val isLoading = stationViewModel.isLoading.value
    val stationList = stationViewModel.stationList.value
    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { stationViewModel.fetchStation(url) }
    )
    LaunchedEffect(Unit) {
        stationViewModel.fetchStation(url)
    }
    Column(modifier = Modifier.pullRefresh(pullRefreshState)
        .fillMaxWidth().padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if(navViewModel.language.value == "kr"){
            Text(text="어린이대공원역 실시간 지하철 정보", fontFamily=fontFamily, fontSize = 22.sp,
                color = colorResource(id = R.color.kudarkgreen), textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp))
        }
        else{
            Text(text="Real-Time Train Info\n\nIn Children's Grand Park Station", fontFamily=fontFamily, fontSize = 22.sp,
                color = colorResource(id = R.color.kudarkgreen), textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp))
        }
        Box (modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ){
            if(navViewModel.language.value == "kr"){
                Text("\n\n화면을 아래로 당기면\n\n실시간 정보를\n\n확인할 수 있어요", fontSize = 32.sp,
                    fontFamily=fontFamily, color = colorResource(id = R.color.kumiddlegreen),
                    textAlign = TextAlign.Center)
            }else{
                Text("\n\nSwipe down\n\nTo Refresh", fontSize = 32.sp,
                    fontFamily=fontFamily, color = colorResource(id = R.color.kumiddlegreen),
                    textAlign = TextAlign.Center)
            }
            StationList(list = stationList)
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                backgroundColor = colorResource(id = R.color.kuhighlightgreen),
                contentColor = colorResource(id = R.color.kudarkgreen)
            )
        }

    }
}