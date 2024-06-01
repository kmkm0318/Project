package com.example.project.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.example.project.Class.Routes
import com.example.project.Class.StationViewModel
import com.example.project.Compose.MetroTopBar
import com.example.project.Compose.TopBar
import com.example.project.R
import org.jsoup.internal.StringUtil.padding


@Composable
fun KonkukUnivStation(navController: NavController, stationViewModel: StationViewModel) {
    Scaffold(
        topBar = {MetroTopBar(navController = navController)}
    ) {
        KonkukUnivStationContent(stationViewModel = stationViewModel, contentPadding = it)
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KonkukUnivStationContent(stationViewModel: StationViewModel, contentPadding:PaddingValues) {
    val url = "http://swopenAPI.seoul.go.kr/api/subway/426e4f7346776f6f393577474f4b51/xml/realtimeStationArrival/0/8/건대입구"
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
    LaunchedEffect(url) {
        stationViewModel.fetchStation(url)
    }
    Box (modifier = Modifier
        .pullRefresh(pullRefreshState)
        .fillMaxWidth()
        .padding(contentPadding),
        contentAlignment = Alignment.Center
    ){
        Text("\n\n화면이 보이지 않는다면\n\n아래로 당겨\n\n새로고침 해주세요", fontSize = 32.sp,
            fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen),
            textAlign = TextAlign.Center)
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = colorResource(id = R.color.kuhighlightgreen),
            contentColor = colorResource(id = R.color.kudarkgreen)
        )
        StationList(list = stationList)
    }
}