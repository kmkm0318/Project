package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Function.getCharacterImage
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R

@Composable
fun CharacterDictionaryScreen(navController: NavController) {
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

    val characterIndex = remember {
        mutableStateOf(-1)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            val title = when (navViewModel.language.value) {
                "kr" -> "캐릭터 도감"
                else -> "Character\n\nDictionary"
            }
            Text(text = title, fontSize = 40.sp, fontFamily = fontFamily, color = textColor)

        }
        Divider()
        val characterList = navViewModel.userData.characterList
        if (characterList.isNullOrEmpty()) {
            val error = when (navViewModel.language.value) {
                "kr" -> "캐릭터가 없습니다"
                else -> "There is no character"
            }
            Text(text = "캐릭터가 없습니다", fontSize = 20.sp, fontFamily = fontFamily, color = textColor)
            return@Column
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(characterList) { idx, it ->
                Box(
                    modifier = Modifier
//                        .fillMaxHeight()
                        .size(200.dp)
                        .padding(15.dp, end = 20.dp)
                        .clickable { characterIndex.value = idx },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    getCharacterImage(characterData = it)
                }
            }
        }
        Divider()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val name = when(characterIndex.value){
                        0->"둘리"
                        1->"프로리"
                        2->"모모"
                        else->"정보가 없습니다"
                    }
                    Text(text = name, fontSize = 40.sp, fontFamily = fontFamily, color = textColor)
                }
                Divider()
            }
            item{
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val doc = when(characterIndex.value){
                        0->{
                            when(characterList[characterIndex.value].level){
                                0->"둘리는 아직 알 속에 있으며, 완전히 부화하지 않았습니다.\n" +
                                        "그의 알껍질은 흰색과 연한 분홍색 그라데이션 색상으로, 매우 아름답고 신비로운 느낌을 줍니다."
                                1->"둘리는 알에서 태어난 귀여운 아기 드래곤입니다. 모험가의 도움을 받아 강력한 드래곤으로 진화할 날을 손꼽아 기다리고 있습니다."
                                2->"2단계로 진화하면서 더욱 강해지고, 드래곤의 특징이 더 두드러지게 나타납니다"
                                3->"둘리는 세 번째 단계로 진화하면서 드래곤으로서의 위엄을 완전히 드러냅니다.\n" +
                                        "강력한 날개를 가지게 되어 비행할 수 있으며, 더욱 강력한 드래곤의 숨결을 내뿜어 진정한 용 전사가 됩니다."
                                else->""
                            }
                        }
                        1->when(characterList[characterIndex.value].level){
                            0->"프로리의 알껍질은 분홍색과 보라색의 그라데이션으로 매우 몽환적이고 신비로운 느낌을 줍니다"
                            1->"프로리는 알에서 태어난 귀여운 아기 개구리입니다.\n" +
                                    "모험가의 도움을 받아 성숙한 개구리가 될 날을 손꼽아 기다리고 있습니다."
                            2->"프로리는 두 번째 단계로 진화하면서 더 커지고 강해졌습니다.\n" +
                                    "몸 색깔이 더욱 선명해져 생기와 활력이 넘칩니다."
                            3->"프로리는 세 번째 단계로 진화하면서 개구리의 위엄을 완전히 드러냅니다."
                            else->""
                        }
                        2->when(characterList[characterIndex.value].level){
                            0->"모모의 알껍질은 아래쪽이 파란색이고 위쪽이 흰색으로 매우 신선해 보입니다."
                            1->"모모는 알에서 태어난 귀여운 강아지입니다.\n" +
                                    "항상 모험가 곁을 따르며 더 강력한 형태로 진화할 준비를 하고 있습니다."
                            2->"모모는 두 번째 단계로 진화하면서 더 활발해지고, 경계심과 보호 능력이 강해지기\n" +
                                    "시작합니다. 사랑하는 사람을 보호할 준비가 되어 있습니다"
                            3->"모모는 세 번째 단계로 진화하면서 날개가 더 커지고, 더 오랜 시간 동안 비행할 수 있습니다. 힘과 속도가 크게 향상됩니다."
                            else->""
                        }
                        else->""
                    }
                    Text(text = doc, fontSize = 25.sp, fontFamily = fontFamily, color = textColor)
                }
            }
        }
    }
}