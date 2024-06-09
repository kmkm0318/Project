package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CorrectAns(navController: NavController) {

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
        Text(fontSize = 100.sp, text = "정답")
        Text(text = "보상으로 보너스 Kupet 경험치를 얻었습니다")
        Button(onClick = { navController.navigate(Routes.Main.route) }) {
            Text(text = "홈으로")
        }
    }
}
