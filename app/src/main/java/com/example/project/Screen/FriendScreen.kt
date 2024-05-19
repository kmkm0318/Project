package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Function.AddFriend
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner

@Composable
fun FriendScreen() {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
    val authManager = AuthManager(activity)

    val friendList = navViewModel.userData.friendList

    val textColor = Color(25, 200, 25)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Green,
        unfocusedBorderColor = Color(25, 200, 25),
        cursorColor = Color(25, 200, 25),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = Color(25, 225, 25),
        contentColor = Color.White,
        disabledContainerColor = Color.Green,
        disabledContentColor = Color.Green
    )

    val studentID = rememberSaveable() {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = studentID.value,
                onValueChange = { studentID.value = it },
                colors = textFieldColors,
                label = {
                    Text(text = "학번", color = textColor)
                },
                modifier = Modifier
                    .size(width = 300.dp, height = 60.dp)
                    .padding(start = 20.dp, end = 10.dp)
            )
            Button(
                onClick = {

                    if (studentID.value.isNullOrEmpty()) {
                        showNotification(activity, "학번을 입력해주세요")
                    }
                    studentID.value?.let {
                        AddFriend(activity, navViewModel.userData, it,
                            { friendData ->
                                val list = navViewModel.userData.friendList?.toMutableList()
                                list?.add(friendData)
                                navViewModel.userData.friendList = list?.toList()
                                authManager.writeToDatabase(navViewModel.userData)
                            },
                            {

                            }
                        )
                    }

                    studentID.value = ""
                },
                modifier = Modifier
                    .padding(end = 25.dp)
                    .fillMaxWidth()
                    .size(width = 100.dp, height = 50.dp),
                colors = buttonColor
            ) {
                Text(text = "추가", fontSize = 20.sp)
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                if (friendList.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {})
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "친구가 없습니다",
                            color = textColor,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            friendList?.let { list ->
                itemsIndexed(list) { idx, it ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {})
                            .padding(8.dp)
                    ) {
                        Text(
                            text = it.studentID,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        drawLine(
                            color = Color(0, 0, 0),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(
                                    4.dp.toPx(),
                                    4.dp.toPx()
                                )
                            )
                        )
                    }
                }
            }

        }
    }

}