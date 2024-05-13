package com.example.project.Class

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database

class NavViewModel : ViewModel(){
    val userid = "greenjoa"
    val userpasswd = "1234"

    var userID:String? = null
    var userPasswd:String? = null

    var loginStatus = mutableStateOf( false )

    fun checkInfo(id:String, passwd:String):Boolean{
        return userid == id && userpasswd == passwd
    }

    fun setUserInfo(id:String, passwd:String){
        userID = id
        userPasswd = passwd
    }

    fun SaveUserData(userData: UserData){
        val database = Firebase.database
        val myRef = database.getReference(userData.studentID)
    }
}
