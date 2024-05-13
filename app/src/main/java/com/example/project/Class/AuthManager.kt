package com.example.project.Class

import android.app.Activity
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class AuthManager(private val activity: Activity) {
    private val auth = Firebase.auth

    fun signInWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    onSuccess()
                } else {
                    // 로그인 실패
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    onFailure()
                }
            }
    }

    fun signUpWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // 가입 성공
                    onSuccess()
                } else {
                    // 가입 실패
                    Toast.makeText(activity, "가입 실패", Toast.LENGTH_SHORT).show()
                    onFailure()
                }
            }
    }


    fun writeToDatabase(userData: UserData, onSuccess: () -> Unit, onFailure: ()->Unit) {
        // 데이터베이스 참조 가져오기
        val database = Firebase.database
        val reference = database.getReference("your_database_reference")

        // userData를 Map으로 변환
        val userDataMap = mapOf(
            "studentID" to userData.studentID,
            "characterList" to userData.characterList?.map { characterData ->
                mapOf(
                    "name" to characterData.name,
                )
            }
        )

        // 데이터 쓰기
        reference.updateChildren(userDataMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                Toast.makeText(activity, "쓰기 실패", Toast.LENGTH_SHORT).show()
                onFailure()
            }
    }

    fun readFromDatabase(onSuccess: (UserData) -> Unit, onFailure: () -> Unit) {
        // 데이터베이스 참조 가져오기
        val database = Firebase.database
        val reference = database.getReference("your_database_reference")

        // 데이터 읽기
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 가져오기 성공
                val studentID = dataSnapshot.child("studentID").getValue(String::class.java)
                val characterList =
                    dataSnapshot.child("characterList").children.map { characterSnapshot ->
                        val name = characterSnapshot.child("name").getValue(String::class.java)
                        CharacterData(name ?: "")

                    }
                val userData = UserData(studentID ?: "", characterList)
                onSuccess(userData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 가져오기 실패
                Toast.makeText(activity, "읽기 실패", Toast.LENGTH_SHORT).show()
                onFailure()
            }
        })
    }
}