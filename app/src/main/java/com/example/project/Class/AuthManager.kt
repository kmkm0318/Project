package com.example.project.Class

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.project.Function.showNotification
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class AuthManager(private val activity: Activity) {
    private val auth = Firebase.auth

    fun signInWithEmail(
        email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
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

    fun signUpWithEmail(
        email: String,
        password: String,
        studentID: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val database = Firebase.database
        val reference = database.getReference("users")
        var isStudentIdExists = false

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (childSnapshot in dataSnapshot.children) {
                    val existingStudentID =
                        childSnapshot.child("studentID").getValue(String::class.java)
                    if (existingStudentID == studentID) {
                        isStudentIdExists = true
                        break
                    }
                }

                if (isStudentIdExists) {
                    showNotification(activity, "이미 해당 학번으로 가입된 사용자가 있습니다.")
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                val characterList = listOf(
                                    CharacterData("duri", 0),
                                    CharacterData("frole", 0),
                                    CharacterData("momo", 0)
                                )
                                val userData = UserData(
                                    studentID = studentID,
                                    characterList = characterList
                                )
                                writeToDatabase(userData, onSuccess)
                                onSuccess()
                            } else {
                                // 가입 실패
                                Toast.makeText(activity, "가입 실패", Toast.LENGTH_SHORT).show()
                                onFailure()
                            }
                        }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                showNotification(activity, "데이터베이스에서 오류가 발생했습니다: ${databaseError.message}")
            }
        })
    }

    fun writeToDatabase(
        userData: UserData,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        // 데이터베이스 참조 가져오기
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val database = Firebase.database

        val userRef = database.getReference("users")
        val studentRef = userRef.child(userId.toString())

        studentRef.setValue(userData).addOnSuccessListener {
            onSuccess()
        }
    }

    fun readFromDatabase(userData: UserData, onSuccess: (UserData) -> Unit, onFailure: () -> Unit) {
        // 데이터베이스 참조 가져오기
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val database = Firebase.database

        val userRef = database.getReference("users")

        val studentRef = userRef.child(userId.toString())

        studentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(userData::class.java)
                user?.let {
                    onSuccess(it)
                    Log.i("1234read", it.studentID)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}