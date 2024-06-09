package com.example.project.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.R

@Composable
fun WrongAns(navController: NavController) {
    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kusemidarkgreen),
        contentColor = colorResource(id = R.color.white),
        disabledContainerColor = colorResource(id = R.color.kusemidarkgreen),
        disabledContentColor = colorResource(id = R.color.white)
    )
    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontSize = 100.sp, text = "오답", fontFamily = fontFamily,
            color = colorResource(id = R.color.kusemidarkgreen),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(60.dp))
        Image(painter = painterResource(id = R.drawable.saddog), contentDescription = "ㅠㅠ",
            modifier = Modifier.width(360.dp).height(360.dp).padding(20.dp))
        Text(text = "다시 도전할 수 있어요!\n\n정답을 맞출 때까지 파이팅!",fontSize = 24.sp,
            fontWeight = FontWeight.Medium,fontFamily = fontFamily,
            color = colorResource(id = R.color.kumiddlegreen))
        Button(onClick = { navController.navigate(Routes.Quiz.route)},
            colors = buttonColor, modifier = Modifier.padding(32.dp)) {
            Text(text = "다시 풀기")
        }
    }
}