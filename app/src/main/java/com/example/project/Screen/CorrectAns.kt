package com.example.project.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.Class.Routes

@Composable
fun CorrectAns(navController: NavController) {
    Column {
        Text(fontSize = 100.sp, text = "정답")
        Button(onClick = { navController.navigate(Routes.Main.route) }) {
            Text(text = "홈으로")
        }
    }
}