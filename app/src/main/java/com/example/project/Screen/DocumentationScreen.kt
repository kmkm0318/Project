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

    val textColor = colorResource(id = R.color.kusemidarkgreen)

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
                    "kr" -> "\nKonkuk University Map : KUM 은 누구나 사용할 수 있는 \n건국대학교 지도 앱입니다.\n이 앱을 통해 학생들은 학교의 여러 동아리 정보를 얻을 수 있고, " +
                            "학교생활을 하면서 걸음 수가 쌓임에 따라 성장하는 \"쿠펫\"의 모습을 확인할 수 있습니다." +
                            "동아리, 소모임은 앱 내 퀴즈를 통해 본인들에 대한 홍보가 가능합니다." +
                            "언제 어디서나 쉽게 이용할 수 있는 KUM으로 건국대학교 캠퍼스를 탐험해보세요.\n" +
                            "\n" +
                            "회원가입 및 로그인: 이메일, 비밀번호, 학번을 입력하여 회원가입을 완료합니다. 이후 이메일과 비밀번호로 로그인 할 수 있습니다.\n" +
                            "\n" +
                            "지도 검색: 지도 화면의 검색창을 통해 건물 및 강의실 위치를 검색할 수 있으며, 검색 결과로 해당 건물과 강의실의 위치가 지도에 표시됩니다.\n" +
                            "\n" +
                            "메뉴: 화면의 메뉴 버튼을 통해 다양한 기능에 접근할 수 있습니다.\n" +
                            "메뉴에서는 학번, 누적 걸음 수와 걸음수에 따른 소모 칼로리가 표시되며, 앱 설명서, 캐릭터 도감, 언어 변경(한국어 및 영어) 기능을 제공합니다.\n" +
                            "\n" +
                            "캐릭터 관리: 사용자의 걸음 수에 따라 캐릭터가 총 4 단계로 진화하며, 다른 캐릭터로 변경할 수 있습니다.\n" +
                            "\n" +
                            "퀴즈: 퀴즈를 풀어 정답을 맞히면 보상을 획득할 수 있으며, 보상은 캐릭터의 성장 속도를 증가시킬 수 있습니다.\n" +
                            "\n" +
                            "친구 관리: 친구의 학번을 입력하여 친구를 추가할 수 있으며, 친구와 위치 및 캐릭터를 공유할 수 있고, 친구 이름도 변경할 수 있습니다\n\n"

                    else -> "\nKonkuk University Map: KUM is a campus map app for everyone at Konkuk University." +
                            "\nThrough this app, students can obtain information about various clubs at the university and track the growth of their \"Ku-pet\" as they accumulate steps throughout their campus life. Clubs and small groups can promote themselves through quizzes within the app. Explore Konkuk University's campus easily anytime, anywhere with KUM." +
                            "\n\nSign Up and Login: Complete the sign-up process by entering your email, password, and student ID number. Afterward, you can log in with your email and password." +
                            "\n\nMap Search: You can search for buildings and lecture rooms through the search bar on the map screen. The search results will display the location of the respective buildings and lecture rooms on the map." +
                            "\n\nMenu: Access various functions through the menu button on the screen. The menu displays your student ID, total steps, and the calories burned according to your step count. It also provides the app manual, character collection, and language change options (Korean and English)." +
                            "\n\nCharacter Management: Your character evolves through four stages based on your step count and can be changed to different characters." +
                            "\n\nQuiz: Solve quizzes to earn rewards, which can accelerate your character's growth." +
                            "\n\nFriend Management: Add friends by entering their student ID numbers, share your location and character with friends, and change your friends' names.\n\n"
                }

                Text(text = doc, fontSize = 24.sp, fontFamily = fontFamily, color = textColor)
            }
        }
    }
}