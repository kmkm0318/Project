package com.example.project.Class

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavViewModel() : ViewModel(){
    var userData = UserData("202011317")

    var userID:String? = ""
    var userPasswd:String? = ""
    var studentID:String? = ""

    var loginStatus = mutableStateOf( false )

    fun updateUserData(readData: UserData){
        userData.studentID = readData.studentID
        userData.characterList = readData.characterList?.toMutableList()
    }
}
