package com.example.project.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.R

@Composable
fun CorrectAns(navController: NavController) {
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
        Text(fontSize = 100.sp, text = "정답",
            fontFamily = fontFamily,
            color = colorResource(id = R.color.kusemidarkgreen),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(60.dp))
        Button(onClick = { navController.navigate(Routes.Main.route) },
            colors = buttonColor, modifier = Modifier.padding(32.dp)) {
            Text(text = "홈으로")
        }
    }
}