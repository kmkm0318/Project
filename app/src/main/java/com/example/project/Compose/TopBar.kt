package com.example.project.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.project.Class.Routes

@Composable
fun TopBar(navController: NavController) {
    Row(horizontalArrangement = Arrangement.Start) {
        IconButton(onClick={navController.navigate(Routes.Map.route){
            popUpTo(Routes.Map.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        } }){
            Icon(Icons.Default.ArrowBackIosNew,null)
        }
    }
//홈으로 돌아가는 뒤로가기 버튼
//    Scaffold (topBar = {
//        TopBar(navController = navController)
//    }){
//        //화면구성
//    }
//이런식으로 사용하면 bottom bar에 홈버튼은 필요없음

}