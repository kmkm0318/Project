package com.example.project.Screen

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.Class.AuthManager
import com.example.project.Class.FriendData
import com.example.project.Class.NavViewModel
import com.example.project.Compose.TopBar
import com.example.project.Function.AddFriend
import com.example.project.Function.UpdateFriendList
import com.example.project.Function.getCharacterImage
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Composable
fun FriendScreen(navController: NavController) {
    Scaffold(topBar = { TopBar(navController = navController) }) { contentPadding ->
        FriendScreenContent(navController, contentPadding)
    }
}

@Composable
fun FriendScreenContent(navController: NavController, contentPadding:PaddingValues) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
    val authManager = AuthManager(activity)

    UpdateFriendList(navViewModel.userData, {
        navViewModel.userData = it
        authManager.writeToDatabase(navViewModel.userData)
    }, {

    })

    val friendList = navViewModel.userData.friendList?.toMutableList()

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

    val studentID = rememberSaveable() {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(contentPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            val title = when (navViewModel.language.value) {
                "kr" -> "친구 목록"
                else -> "Friend List"
            }
            Text(text = title, fontSize = 40.sp, fontFamily = fontFamily, color = textColor)

        }
        Divider()
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
                    val studentID = when (navViewModel.language.value) {
                        "kr" -> "학번"
                        else -> "StudentID"
                    }
                    Text(text = studentID, color = textColor, fontFamily = fontFamily)
                },
                modifier = Modifier
                    .size(width = 275.dp, height = 60.dp)
                    .padding(start = 20.dp, end = 10.dp)
            )
            Button(
                onClick = {

                    if (studentID.value.isNullOrEmpty()) {
                        val studentID = when (navViewModel.language.value) {
                            "kr" -> "학번을 입력해주세요"
                            else -> "Please Type StudentID"
                        }
                        showNotification(activity, studentID)
                    }
                    else{
                        studentID.value?.let {
                            AddFriend(activity, navViewModel.userData, it, { friendData ->
                                val list = navViewModel.userData.friendList?.toMutableList()
                                list?.add(friendData)
                                navViewModel.userData.friendList = list?.toList()
                                authManager.writeToDatabase(navViewModel.userData, onSuccess = {
                                    studentID.value = ""
                                })
                            }, {})
                        }
                    }
                },
                modifier = Modifier
                    .padding(end = 25.dp)
                    .size(width = 125.dp, height = 50.dp)
                    .align(Alignment.CenterVertically),
                colors = buttonColor
            ) {
                val add = when (navViewModel.language.value) {
                    "kr" -> "추가"
                    else -> "Add"
                }
                Text(
                    text = add,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    fontFamily = fontFamily,
                )
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
                        val noFriend = when (navViewModel.language.value) {
                            "kr" -> "친구가 없습니다"
                            else -> "You Don't Have any Friend"
                        }
                        Text(
                            text = noFriend,
                            color = textColor,
                            fontSize = 25.sp,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontFamily = fontFamily
                        )
                    }
                }
            }
            friendList?.let { list ->
                itemsIndexed(list) { idx, it ->
                    friendRow(navViewModel = navViewModel, friendData = it, onClick = {
                        navViewModel.friendData = it
                    }, saveData = { data ->
                        friendList[idx] = data
                        authManager.writeToDatabase(navViewModel.userData)
                    })
                    Divider()
                }
            }

        }
    }

}

@Composable
fun friendRow(
    navViewModel: NavViewModel,
    friendData: FriendData,
    onClick: () -> Unit,
    saveData: (FriendData) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    val changeName = remember {
        mutableStateOf(false)
    }

    val friendName = remember {
        mutableStateOf(friendData.name)
    }

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val textColor = colorResource(id = R.color.kudarkgreen)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.kumiddlegreen),
        unfocusedBorderColor = colorResource(id = R.color.kudarkgreen),
        cursorColor = colorResource(id = R.color.kudarkgreen),
        focusedTextColor = colorResource(id = R.color.kudarkgreen),
        unfocusedTextColor = colorResource(id = R.color.kumiddlegreen),
        focusedContainerColor = colorResource(id = R.color.kuhighlightgreen),
        unfocusedContainerColor = colorResource(id = R.color.kuhighlightgreen)
    )

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = Color.White,
        disabledContainerColor = Color.Green,
        disabledContentColor = Color.Green
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    onClick()
                    expanded.value = !expanded.value
                })
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(75.dp)
            ) {
                getCharacterImage(characterData = friendData.characterData)
            }
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) {
                if (changeName.value) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        value = friendName.value,
                        onValueChange = {
                            friendName.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {

                        })
                    )
                } else {
                    Text(
                        text = friendData.name,
                        fontSize = 25.sp,
                        color = textColor,
                        fontFamily = fontFamily
                    )
                }
                Text(
                    text = friendData.studentID,
                    fontSize = 15.sp,
                    color = textColor,
                    fontFamily = fontFamily
                )
            }

        }
        if (expanded.value) {
            Column(
                modifier = Modifier.fillMaxSize()
//                    .background(Color(225, 255, 225))
            ) {
                Divider()
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
//                        modifier = Modifier.size(150.dp, 50.dp),
                        colors = buttonColor,
                        onClick = {
                            if (changeName.value) {
                                friendData.name = friendName.value
                                saveData(friendData)
                            }
                            changeName.value = !changeName.value

                        }) {
                        val changeName = when (navViewModel.language.value) {
                            "kr" -> "이름 바꾸기"
                            else -> "Change\nName"
                        }
                        Text(
                            text = changeName, fontFamily = fontFamily, fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Button(
//                        modifier = Modifier.size(150.dp, 50.dp),
                        colors = buttonColor,
                        onClick = {
                            onClickLocationShareButton(navViewModel, friendData.studentID)
                        }) {
                        val sendLocation = when (navViewModel.language.value) {
                            "kr" -> "위치 보내기"
                            else -> "Send\nLocation"
                        }
                        Text(
                            text = sendLocation, fontFamily = fontFamily, fontSize = 20.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

fun onClickLocationShareButton(navViewModel: NavViewModel, friendID: String) {
    val userRef = Firebase.database.getReference("users")
    val friendRef = userRef.orderByChild("studentID").equalTo(friendID)

    friendRef.addListenerForSingleValueEvent(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            for(friend in snapshot.children){
                friend.ref.child("friendLocationLat").setValue(navViewModel.userData.locationNowLat)
                friend.ref.child("friendLocationLng").setValue(navViewModel.userData.locationNowLng)
                friend.ref.child("transactingfriendname").setValue(navViewModel.userData.studentID)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
}