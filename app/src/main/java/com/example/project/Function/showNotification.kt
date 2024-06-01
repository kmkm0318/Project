package com.example.project.Function

import android.app.Activity
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun showNotification(activity: Activity, message: String) {
    GlobalScope.launch(Dispatchers.Main) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}