package com.example.project.Screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.NavViewModel
import com.example.project.Compose.TopBar
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.android.play.integrity.internal.c
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Quiz(
    val question: String = "",
    val ans1: String = "",
    val ans2: String = "",
    val ans3: String = "",
    val ans4: String = "",
    val correctAns: String = ""
)

@Composable
fun QuizScreen(navController: NavController) {
    Scaffold(topBar = { TopBar(navController = navController) }) { contentPadding ->
        QuizScreenContent(navController, contentPadding)
    }
}

@Composable
fun QuizScreenContent(navController: NavController, contentPadding: PaddingValues) {

    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var Quiz by remember {
        mutableStateOf<Quiz>(Quiz())
    }

    val QuizList = mutableListOf<Quiz>()

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDateTime = currentDateTime.format(formatter)

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = colorResource(id = R.color.white),
        disabledContainerColor = colorResource(id = R.color.kumiddlegreen),
        disabledContentColor = colorResource(id = R.color.white)
    )

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    if (navViewModel.userData.lastQuizDate == formattedDateTime) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "오늘의 퀴즈를\n\n이미 풀고\n\n보상을 받았어요",fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,fontFamily = fontFamily,
                color = colorResource(id = R.color.kusemidarkgreen),
                textAlign = TextAlign.Center)
            Image(painter = painterResource(id = R.drawable.sadturtle), contentDescription = "ㅠㅠ",
                modifier = Modifier
                    .width(360.dp)
                    .height(360.dp)
                    .padding(40.dp))
            Text(text = "내일 또 보상을 받을 수 있어요!",fontSize = 24.sp,
                fontWeight = FontWeight.Medium,fontFamily = fontFamily,
                color = colorResource(id = R.color.kumiddlegreen))
        }

    } else {

        val db = Firebase.firestore
        db.collection("Quiz")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val dataMap = document.data
                    Log.d("db", "${document.id} => ${dataMap["question"].toString()}")
                    Quiz = Quiz(
                        dataMap["question"].toString(),
                        dataMap["ans1"].toString(),
                        dataMap["ans2"].toString(),
                        dataMap["ans3"].toString(),
                        dataMap["ans4"].toString(),
                        dataMap["correct_ans"].toString()
                    )
                    QuizList.add(Quiz)
                    Log.w("quizList", "$QuizList")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("db", "Error getting documents.", exception)
            }

        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontSize = 40.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    textAlign = TextAlign.Center,
                    text = "오늘의 퀴즈",
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.kudarkgreen),
                    fontFamily = fontFamily
                )
                Text(
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center,
                    text = Quiz.question,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.kusemidarkgreen),
                    fontFamily = fontFamily
                )
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(60.dp),
                        border = BorderStroke(2.dp, colorResource(id = R.color.kumiddlegreen)),
                        colors = buttonColor,
                        shape = RoundedCornerShape(20),
                        onClick = { checkAns(1, Quiz.correctAns.toInt(), navController) }) {
                        Text(fontSize = 20.sp, text = Quiz.ans1, fontWeight = FontWeight.SemiBold,fontFamily = fontFamily)
                    }
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(60.dp),
                        border = BorderStroke(2.dp, colorResource(id = R.color.kumiddlegreen)),
                        colors = buttonColor,
                        shape = RoundedCornerShape(20),
                        onClick = { checkAns(2, Quiz.correctAns.toInt(), navController) }) {
                        Text(fontSize = 20.sp, text = Quiz.ans2, fontWeight = FontWeight.SemiBold,fontFamily = fontFamily)
                    }
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(60.dp),
                        border = BorderStroke(2.dp, colorResource(id = R.color.kumiddlegreen)),
                        colors = buttonColor,
                        shape = RoundedCornerShape(20),
                        onClick = { checkAns(3, Quiz.correctAns.toInt(), navController) }) {
                        Text(fontSize = 20.sp, text = Quiz.ans3, fontWeight = FontWeight.SemiBold,fontFamily = fontFamily)
                    }
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 6.dp, end = 6.dp, bottom = 20.dp)
                        .height(60.dp),
                        border = BorderStroke(2.dp, colorResource(id = R.color.kumiddlegreen)),
                        colors = buttonColor,
                        shape = RoundedCornerShape(20),
                        onClick = { checkAns(4, Quiz.correctAns.toInt(), navController) }) {
                        Text(fontSize = 20.sp, text = Quiz.ans4, fontWeight = FontWeight.SemiBold,fontFamily = fontFamily)
                    }
                }
            }
        }
    }
}

fun checkAns(input: Int, ans: Int, navController: NavController) {
    if (input == ans) {
        Log.d("quiz", "correct answer")
        navController.navigate(Routes.CorrectAns.route)
    } else {
        Log.d("quiz", "wrong answer")
        navController.navigate(Routes.WrongAns.route)
    }
}
