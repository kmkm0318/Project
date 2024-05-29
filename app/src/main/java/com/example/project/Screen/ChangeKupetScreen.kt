package com.example.project.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes

//@Composable
//fun ChangeKupetScreen(navController: NavController, navViewModel: NavViewModel) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Button(onClick = {
//            navViewModel.userData.characterIndex = 0
//            navViewModel.DBdataUpdate()
//            navController.navigate(Routes.Kupet.route)
//        }) {
//            Text(text = "duri")
//        }
//        Button(onClick = { /*TODO*/ }) {
//            navViewModel.userData.characterIndex = 1
//            navViewModel.DBdataUpdate()
//            navController.navigate(Routes.Kupet.route)
//            Text(text = "frole")
//        }
//        Button(onClick = { /*TODO*/ }) {
//            navViewModel.userData.characterIndex = 2
//            navViewModel.DBdataUpdate()
//            navController.navigate(Routes.Kupet.route)
//            Text(text = "momo")
//        }
//    }
//}