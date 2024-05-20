package com.example.project.Screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.Class.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MenuScreen(navController: NavHostController) {
    val menuList = listOf("항목 1", "항목 2", "항목 3", "로그아웃")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        itemsIndexed(menuList) { idx, it ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        if (idx == 3) {
                            logout(navController)
                        }
                    })
                    .padding(8.dp)
            ) {
                Text(
                    text = it,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                drawLine(
                    color = Color(0, 0, 0),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx()))
                )
            }
        }
    }
}

fun logout(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    auth.signOut()

    navController.navigate(Routes.Login.route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}