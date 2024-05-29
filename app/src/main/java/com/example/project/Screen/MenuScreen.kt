package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes
import com.example.project.Function.saveLanguage
import com.example.project.Compose.TopBar
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.firebase.auth.FirebaseAuth



@Composable
fun MenuScreen(navController: NavHostController){
    Scaffold (topBar = {
        TopBar(navController = navController)
    }){contentPadding->
        MenuScreenContent(navController = navController, contentPadding)
    }
}
@Composable
fun MenuScreenContent(navController: NavHostController, contentPadding:PaddingValues) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
    val authManager = AuthManager(activity)

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val textColor = colorResource(R.color.kudarkgreen)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Green,
        unfocusedBorderColor = Color(25, 200, 25),
        cursorColor = Color(25, 200, 25),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = Color(25, 200, 25),
        contentColor = Color.White,
        disabledContainerColor = Color.Green,
        disabledContentColor = Color.Green
    )

    var menuList_en = listOf(
        "Searching Building",
        "Searching Lecture Room",
        "Public Transportation Information",
        "Change Language",
        "Log Out"
    )
    var menuList_kr = listOf("건물 검색", "강의실 검색", "대중교통 정보 확인", "언어 변경", "로그아웃")

    var menuList = menuList_en
    if (navViewModel.language.value == "kr") {
        menuList = menuList_kr
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = navViewModel.userData.studentID,
                        fontSize = 50.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val dailyStep = when (navViewModel.language.value) {
                        "kr" -> "일일 걸음 수 : "
                        else -> "Daily Step Count : "
                    }
                    Text(
                        text = dailyStep,
                        fontFamily = fontFamily,
                        color = textColor,
                        fontSize = 20.sp
                    )
                    Text(
                        text = navViewModel.userData.steps_current.toString(),
                        fontFamily = fontFamily,
                        color = textColor,
                        fontSize = 25.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val wholeStep = when (navViewModel.language.value) {
                        "kr" -> "누적 걸음 수 : "
                        else -> "Whole Step Count : "
                    }
                    Text(
                        text = wholeStep,
                        fontFamily = fontFamily,
                        color = textColor,
                        fontSize = 20.sp
                    )
                    Text(
                        text = navViewModel.userData.steps_total.toString(),
                        fontFamily = fontFamily,
                        color = textColor,
                        fontSize = 25.sp
                    )
                }
                Spacer(modifier = Modifier.size(50.dp))
            }
        }
        itemsIndexed(menuList) { idx, it ->
            Divider(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        if (idx == 3) {
                            if (navViewModel.language.value == "kr") {
                                navViewModel.language.value = "en"
                            } else {
                                navViewModel.language.value = "kr"
                            }
                            saveLanguage(context, navViewModel.language.value)
                        } else if (idx == 4) {
                            logout(navController) {
                                navViewModel.loginStatus.value = false
                            }
                        }
                    })
                    .padding(8.dp)
            ) {
                Text(
                    text = it,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    fontFamily = fontFamily,
                    color = textColor
                )
            }
        }
    }
}

fun logout(navController: NavHostController, onSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    auth.signOut()
    onSuccess()
    navController.navigate(Routes.Login.route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}