package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController, authManager: AuthManager, activity: Activity) {

    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Screen", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "${navViewModel.userID}님 환영합니다.",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        LaunchedEffect(key1 = Unit) {
            delay(1000)
            navViewModel.loginStatus.value = true
            navController.navigate(Routes.Main.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}