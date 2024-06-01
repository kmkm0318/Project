package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R

@Composable
fun DocumentationScreen(navController: NavHostController) {
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

    val textColor = colorResource(id = R.color.kudarkgreen)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.kudarkgreen),
        unfocusedBorderColor = colorResource(id = R.color.kumiddlegreen),
        cursorColor = colorResource(id = R.color.kudarkgreen),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = Color.White,
        disabledContainerColor = Color.Green,
        disabledContentColor = Color.Green
    )



    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                val title = when (navViewModel.language.value) {
                    "kr" -> "앱 설명서"
                    else -> "Documentation"
                }
                Text(text = title, fontSize = 40.sp, fontFamily = fontFamily, color = textColor)

            }
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ){
                val doc = when (navViewModel.language.value) {
                    "kr" -> "Konkuk map 은 누구나 사용할 수 있는 건국대학교 지도 앱입니다. 이 앱을 통해 학생들은 학습과 생활을 더 효율적이고 재미있게 관리할 수 있으며, 친구들과 함께 더 많은 경험을 나눌 수 있습니다. 언제 어디서나 쉽게 이용할 수 있는 konkuk map 앱으로 건국대학교 캠퍼스를 탐험해보세요.\n" +
                            "\n" +
                            "회원가입 및 로그인: 이메일, 비밀번호, 학번을 입력하여 회원가입을 완료합니다. 이후 이메일과 비밀번호로 로그인 할 수 있습니다.\n" +
                            "\n" +
                            "지도 검색: 지도 화면의 검색창을 통해 건물 및 강의실 위치를 검색할 수 있으며, 검색 결과로 해당 건물과 강의실의 위치가 지도에 표시되며, 추천 경로가 안내됩니다\n" +
                            "\n" +
                            "메뉴: 화면의 메뉴 버튼을 통해 다양한 기능에 접근할 수 있습니다.\n" +
                            "메뉴에서는 학번, 일일 걸음 수, 누적 걸음 수가 표시되며, 앱 설명서, 캐락터 도감, 언어 변경(한국어 및 영어) 기능을 제공합니다.\n" +
                            "\n" +
                            "캐릭터 관리: 사용자의 걸음 수에 따라 캐릭터가 총 4 단계로 진화하며, 다른 캐릭터로 변경할 수 있습니다.\n" +
                            "\n" +
                            "퀴즈: 퀴즈를 풀어 정답을 맞히면 보상을 획득할 수 있으며, 보상은 캐릭터의 성장 속도를 증가시킬 수 있습니다.\n" +
                            "\n" +
                            "친구 관리: 친구의 학번을 입력하여 친구를 추가할 수 있으며, 친구와 위치 및 캐릭터를 공유할 수 있고, 친구 이름도 변경할 수 있습니다"

                    else -> "Konkuk map is a Konkuk University guidance app that anyone can use. Through this app, students can manage their learning and life more efficiently and in a fun way, and share more experiences with friends. Explore Konkuk University's campus with the Konkuk map app, which is easily available anytime, anywhere.\n" +
                            "\n" +
                            "Membership and login: Complete membership registration by entering email, password, and student number. After that, you can log in with email and password.\n" +
                            "\n" +
                            "Search for a map: The search window on the map screen allows you to search for the location of buildings and classrooms, and the search results show the location of the building and classrooms on the map, and guide you through the recommended route\n" +
                            "\n" +
                            "Menu: Various functions can be accessed through the menu buttons on the screen.\n" +
                            "The menu displays student numbers, daily steps, and cumulative steps, and provides an app manual, a character guide, and language change (Korean and English).\n" +
                            "\n" +
                            "Character Management: Characters evolve into a total of 4 steps depending on the number of steps you take, and you can change them to other characters.\n" +
                            "\n" +
                            "Quiz: You can earn rewards if you answer the quiz correctly, and rewards can increase the rate of your character's growth.\n" +
                            "\n" +
                            "Manage Friends: You can add friends by entering their student number, share locations and characters with friends, and change friends' names"
                }

                Text(text = doc, fontSize = 24.sp, fontFamily = fontFamily, color = textColor)
            }
        }
    }
}