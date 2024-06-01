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
fun WrongAns(navController: NavController) {
    Column {
        Text(fontSize = 100.sp, text = "오답")
        Button(onClick = { navController.navigate(Routes.Quiz.route) }) {
            Text(text = "다시 풀기")
        }
    }
}