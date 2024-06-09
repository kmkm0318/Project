package com.example.project.Screen

import Routes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.project.Class.NavViewModel
import com.example.project.Class.StationData
import com.example.project.Class.StationViewModel
import com.example.project.Compose.TopBar
import com.example.project.R
import com.google.android.play.integrity.internal.f


@Composable
fun MetroArrivalScreen(navController: NavController, navViewModel: NavViewModel) {
    Scaffold(
        topBar = { TopBar(navController = navController) }
    ) { contentPadding ->
        MetroScreenContent(navController, contentPadding, navViewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MetroScreenContent(navController: NavController, contentPadding: PaddingValues, navViewModel: NavViewModel) {

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = colorResource(id = R.color.white),
        disabledContainerColor = colorResource(id = R.color.kudarkgreen),
        disabledContentColor = colorResource(id = R.color.white)
    )

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(navViewModel.language.value == "kr"){
            Text(text="실시간 열차 정보", fontFamily=fontFamily, fontSize = 40.sp,
                textAlign = TextAlign.Center,color = colorResource(id = R.color.kudarkgreen),
                modifier = Modifier.padding(12.dp))
        }
        else{
            Text(text="Real-Time Subway\n\nInformation",
                fontFamily=fontFamily, fontSize = 30.sp, textAlign = TextAlign.Center,
                color = colorResource(id = R.color.kudarkgreen),
                modifier = Modifier.padding(12.dp)
            )
        }

        Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(onClick = { navController.navigate(Routes.KonkukUnivStation.route) },
                shape = RoundedCornerShape(20),
                colors = buttonColor,
                modifier = Modifier
                    .padding(24.dp)
                    .width(320.dp)
                    .height(100.dp),
                ) {
                if(navViewModel.language.value == "kr"){
                    Text(text="건대입구역\n\n실시간 열차확인", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
                else{
                    Text(text="Konkuk University\n\nStation", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
            Button(onClick = { navController.navigate(Routes.ChildrensGrandPark.route)},
                shape = RoundedCornerShape(20),
                colors = buttonColor,
                modifier = Modifier
                    .padding(24.dp)
                    .width(320.dp)
                    .height(100.dp)) {
                if(navViewModel.language.value == "kr"){
                    Text(text="어린이대공원역\n\n실시간 열차확인", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
                else{
                    Text(text="Children's GrandPark\n\nStation", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
            Button(onClick = { navController.navigate(Routes.KueuiStation.route) },
                shape = RoundedCornerShape(20),
                colors = buttonColor,
                modifier = Modifier
                    .padding(24.dp)
                    .width(320.dp)
                    .height(100.dp)) {
                if(navViewModel.language.value == "kr"){
                    Text(text="구의역\n\n실시간 열차확인", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
                else{
                    Text(text="Kueui\n\nStation", fontFamily=fontFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun StationList(list: List<StationData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { item ->
            StationItem(item)
        }
    }
}

@Composable
fun StationItem(stationData: StationData) {
    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.kuhighlightgreen))
            .border(1.dp, colorResource(id = R.color.kumiddlegreen)),
            contentAlignment = Alignment.Center){
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(stationData.arrivalMsg1, fontSize = 16.sp,
                        fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                    if(!stationData.arrivalMsg1.contains("도착") &&
                        !stationData.arrivalMsg1.contains("출발")&&
                        !stationData.arrivalMsg1.contains("진입")){
                        Text(" 도착예정", fontSize = 16.sp,
                            fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                    }

                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text("열차 현재 위치 : ", fontSize = 16.sp,
                        fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                    Text(stationData.arrivalMsg2, fontSize = 16.sp,
                        fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(stationData.line, fontSize = 16.sp,
                        fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                    Text("호선", fontSize = 16.sp,
                        fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                }
                Text(stationData.trainLineName, fontSize = 16.sp,
                    fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
                Text(stationData.upDnLine, fontSize = 16.sp,
                    fontFamily=fontFamily, color = colorResource(id = R.color.kudarkgreen))
            }
        }

    }
}