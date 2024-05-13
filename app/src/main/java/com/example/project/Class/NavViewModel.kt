package com.example.project.Class

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

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
}
