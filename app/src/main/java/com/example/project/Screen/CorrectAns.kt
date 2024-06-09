package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val activity = LocalContext.current as Activity
    val authManager = AuthManager(activity)

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDateTime = currentDateTime.format(formatter)

    navViewModel.userData.lastQuizDate = formattedDateTime
    navViewModel.userData.characterList[navViewModel.userData.characterIndex].prev_steps_total += 100

    authManager.writeToDatabase(navViewModel.userData)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(fontSize = 80.sp, text = "정답",
            fontFamily = fontFamily,
            color = colorResource(id = R.color.kusemidarkgreen),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(30.dp))
        Text(fontSize = 32.sp, text = "보상으로\n\n보너스 발걸음을\n\n얻었습니다!",
            fontFamily = fontFamily,
            color = colorResource(id = R.color.kumiddlegreen),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.happyturtle), contentDescription = "ㅋㅋ",
            modifier = Modifier.width(360.dp).height(360.dp).padding(20.dp))
        Text(text = "아쉽지만 오늘의 퀴즈는 끝이에요...\n\n내일 또 보상을 받을 수 있어요!",fontSize = 24.sp,
            fontWeight = FontWeight.Medium,fontFamily = fontFamily,
            color = colorResource(id = R.color.kumiddlegreen))
        Button(onClick = { navController.navigate(Routes.Main.route) },
            colors = buttonColor, modifier = Modifier.padding(24.dp)) {
            Text(text = "홈으로")
        }
    }
}
