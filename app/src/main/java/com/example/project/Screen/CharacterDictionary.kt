package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.graphicsLayer
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
                .size(100.dp), contentAlignment = Alignment.Center
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
            Text(text = error, fontSize = 20.sp, fontFamily = fontFamily, color = textColor)
            return@Column
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .background(Color(225, 225, 225)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(characterList) { idx, it ->
                var alphaVal = 0.5f
                if (characterIndex.value == idx) {
                    alphaVal = 1f
                }
                Box(
                    modifier = Modifier
//                        .fillMaxHeight()
                        .size(200.dp)
                        .padding(15.dp, end = 20.dp)
//                        .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                        .graphicsLayer { alpha = alphaVal }
                        .clickable { characterIndex.value = idx },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    getCharacterImage(characterData = it)
                }
            }
        }
        Divider()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                if (characterIndex.value == -1) return@item
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val name = when (characterIndex.value) {
                        0 -> "둘리"
                        1 -> "쿠롱이"
                        2 -> "모모"
                        else -> "정보가 없습니다"
                    }
                    Text(
                        text = name + " Lv" + characterList[characterIndex.value].level.toString(),
                        fontSize = 40.sp,
                        fontFamily = fontFamily,
                        color = textColor
                    )
                }
                Divider()
            }
            item {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    val doc = when (characterIndex.value) {
                        0 -> when (characterList[characterIndex.value].level) {
                            0 -> "오리드래곤의 알이네요! 둘리의 알은 여느 오리드래곤처럼 주황색이 아닙니다! 강력한 오리드래곤 \"둘둘\"과 \"리리\"의 딸, 둘리는 오리드래곤의 희망입니다."
                            1 -> "둘리는 알에서 방금 태어났습니다! 모험가의 도움을 받아 강력한 드래곤으로 진화할 날을 손꼽아 기다리고 있습니다."
                            2 -> "진화한 둘리는 오리드래곤의 특징, 갈기가 더 두드러지게 나타납니다! 오리드래곤으로서 각성하기 시작하며 푸른 냉기불꽃을 뿜을 수 있게 됩니다."
                            3 -> "둘리는 세 번째 단계로 진화하면서 오리드래곤의 왕으로 등극합니다!\n" + "거대한 날개로 비행할 수 있으며, 더욱 강력한 냉기불꽃으로 기선을 제압합니다."

                            else -> ""
                        }

                        1 -> when (characterList[characterIndex.value].level) {
                            0 -> "쿠롱이는 전설의 왕거북, \"쿵쿵\"의 강인함을 물려받았습니다! 쿠롱이는 알 속에서 힘을 모으며 새로운 모험을 준비하고 있습니다.\n"
                            1 -> "쿠롱이는 막 부화한 상태로, 작지만 단단한 몸을 가지고 있습니다. 호기심이 많지만 부끄럼도 많은 쿠롱이는 아무도 없는 밤, 몰래 일감호를 탐험하는 것을 좋아합니다."
                            2 -> "쿠롱이는 급격히 성장해 단단한 몸과 뿔, 그리고 \"쿵쿵\"의 후계자만이 가질 수 있는 강철 등껍질을 가지게 되었습니다. 이제 폭주기관차도 쿠롱이를 다치게 할 수 없습니다!"
                            3 -> "드디어 쿠롱이는 전설의 왕거북, \"쿵쿵\"을 넘어서, 거북신 용왕이 되었습니다! 쿠롱이는 이제 거북이의 왕으로서 모험가님과 함께할겁니다."
                            else -> ""
                        }

                        2 -> when (characterList[characterIndex.value].level) {
                            0 -> "모모의 알은 평범해보이지만, 곧 부화할 막강한 귀여움을 숨기고 있습니다..."
                            1 -> "모모는 종을 알 수 없는 귀염둥이 강아지입니다.\n" + "넘치는 충성심으로 항상 모험가님의 곁을 따르며, 모험가님에게 일어날 수 있는 안 좋은 일들을 막아주는 행운의 아이입니다!"
                            2 -> "모모는 요즘 꼬리가 점점 길어지고 등이 자꾸 간지럽다고 합니다. 등을 긁어주다 만져지는 이건 뭐지..? 날개....?"
                            3 -> "앗! 오리무중이었던 모모의 종을 드디어 알겠군요! 모모는 \"용강아지\"였습니다! 모모는 거대한 날개로 세상을 탐험하며 모험가님에게 항상 든든한 파트너가 되어줄거에요!"
                            else -> ""
                        }

                        else -> ""
                    }
                    Text(text = doc, fontSize = 25.sp, fontFamily = fontFamily, color = textColor)
                }
            }
        }
    }
}