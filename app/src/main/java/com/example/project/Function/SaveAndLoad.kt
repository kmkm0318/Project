package com.example.project.Function

import android.content.Context


fun saveLanguage(context : Context, language:String){
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Language", language)
    editor.apply()
}

fun loadLanguage(context:Context):String{
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val res = sharedPreferences.getString("Language", "kr")
    return res?:"kr"
}