package com.example.project.Function

import android.app.Activity
import android.util.Log
import com.example.project.Class.CharacterData
import com.example.project.Class.FriendData
import com.example.project.Class.UserData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

fun AddFriend(
    activity: Activity,
    userData: UserData,
    studentID: String,
    onSuccess: (FriendData) -> Unit,
    onFailure: () -> Unit
) {
    if (studentID == userData.studentID) {
        showNotification(activity, "본인의 학번입니다.")
        return
    }

    userData.friendList?.forEach {
        if (it.studentID == studentID) {
            showNotification(activity, "이미 친구인 사용자입니다.")
            return
        }
    }

    val database = Firebase.database
    val reference = database.getReference("users")

    reference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            Log.i("123123", "123123")
            var isStudentIdExists = false
            for (childSnapshot in dataSnapshot.children) {
                val existingStudentID =
                    childSnapshot.child("studentID").getValue(String::class.java)
                if (existingStudentID == studentID) {
                    isStudentIdExists = true
                    val name = childSnapshot.child("name").getValue(String::class.java)
                    val characterData =
                        childSnapshot.child("characterData").getValue(CharacterData::class.java)
                    val friendData =
                        FriendData(name ?: "default", studentID, characterData ?: CharacterData())
                    onSuccess(friendData)
                    break
                }
            }

            if (!isStudentIdExists) {
                showNotification(activity, "그 학번에 해당하는 사용자는 없습니다.")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            showNotification(activity, "오류가 발생했습니다.")
        }
    })
}
