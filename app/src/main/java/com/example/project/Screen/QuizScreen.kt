package com.example.project.Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.Class.Routes
import com.example.project.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


data class Quiz(val question:String, val ans1:String, val ans2:String, val ans3:String, val ans4:String, val correctAns:String)


@Composable
fun QuizScreen(navController: NavController) {

    var Quiz by remember {
        mutableStateOf<Quiz>(Quiz("", "", "", "", "", ""))
    }

    val db = Firebase.firestore
    db.collection("Quiz")
        .get()
        .addOnSuccessListener {result ->
            for (document in result){
                val dataMap = document.data
                //Log.d("db", "${document.id} => ${dataMap["question"].toString()}")
                Quiz = Quiz(dataMap["question"].toString(), dataMap["ans1"].toString(), dataMap["ans2"].toString(), dataMap["ans3"].toString(), dataMap["ans4"].toString(), dataMap["correct_ans"].toString())
            }
        }
        .addOnFailureListener {exception ->
            Log.w("db", "Error getting documents.", exception)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "오늘의 퀴즈"
            )
            Text(
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = Quiz.question
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(20.dp), colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.kumiddlegreen)), onClick = { checkAns(1, Quiz.correctAns.toInt(), navController) }) {
                Text(modifier = Modifier.padding(10.dp), fontSize = 20.sp, text = Quiz.ans1)
            }
            Button(modifier = Modifier.padding(20.dp), colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.kumiddlegreen)),onClick = { checkAns(2, Quiz.correctAns.toInt(), navController) }) {
                Text(modifier = Modifier.padding(10.dp),fontSize = 20.sp,text = Quiz.ans2)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(20.dp), colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.kumiddlegreen)),onClick = { checkAns(3, Quiz.correctAns.toInt(), navController) }) {
                Text(modifier = Modifier.padding(10.dp),fontSize = 20.sp,text = Quiz.ans3)
            }
            Button(modifier = Modifier.padding(20.dp), colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.kumiddlegreen)),onClick = { checkAns(4, Quiz.correctAns.toInt(), navController) }) {
                Text(modifier = Modifier.padding(10.dp),fontSize = 20.sp,text = Quiz.ans4)
            }
        }
    }
}

fun checkAns(input:Int, ans:Int, navController: NavController){
    if(input == ans){
        Log.d("quiz", "correct answer")
        navController.navigate(Routes.CorrectAns.route)
    }
    else{
        Log.d("quiz", "wrong answer")
        navController.navigate(Routes.WrongAns.route)
    }
}

