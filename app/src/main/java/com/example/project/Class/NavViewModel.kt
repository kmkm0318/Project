package com.example.project.Class

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavViewModel() : ViewModel(){
    var userData = UserData("0000")
    var language = mutableStateOf("kr")

    var userID:String? = ""
    var userPasswd:String? = ""
    var studentID:String? = ""

    var loginStatus = mutableStateOf( false )

    var friendData = FriendData()
}
