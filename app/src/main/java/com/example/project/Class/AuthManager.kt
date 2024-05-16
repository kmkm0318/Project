package com.example.project.Class

import android.app.Activity
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

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isStudentIdExists = false
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
                                // 가입 성공
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


    fun writeToDatabase(userData: UserData, onSuccess: () -> Unit, onFailure: () -> Unit) {
        // 데이터베이스 참조 가져오기
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val database = Firebase.database
        val reference = database.getReference("users").child(userId ?: "default")

        // userData를 Map으로 변환
        val userDataMap = mapOf("studentID" to userData.studentID,
            "steps_current" to userData.steps_current,
            "steps_total" to userData.steps_total,
            "characterIndex" to userData.characterIndex,
            "characterList" to userData.characterList?.map { characterData ->
                mapOf(
                    "name" to characterData.name,
                    "level" to characterData.level,
                    "steps_current" to characterData.steps_current,
                    "steps_total" to characterData.steps_total
                )
            }

        )

        // 데이터 쓰기
        reference.updateChildren(userDataMap).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { e ->
            Toast.makeText(activity, "쓰기 실패", Toast.LENGTH_SHORT).show()
            onFailure()
        }
    }

    fun readFromDatabase(onSuccess: (UserData) -> Unit, onFailure: () -> Unit) {
        // 데이터베이스 참조 가져오기
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val database = Firebase.database
        val reference = database.getReference("users").child(userId ?: "default")

        // 데이터 읽기
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 가져오기 성공
                val studentID = dataSnapshot.child("studentID").getValue(String::class.java)
                val steps_current = dataSnapshot.child("steps_current").getValue(Int::class.java)
                val steps_total = dataSnapshot.child("steps_total").getValue(Int::class.java)
                val characterIndex = dataSnapshot.child("characterIndex").getValue(Int::class.java)
                val characterList =
                    dataSnapshot.child("characterList").children.map { characterSnapshot ->
                        val name = characterSnapshot.child("name").getValue(String::class.java)
                        val steps_current =
                            characterSnapshot.child("steps_current").getValue(Int::class.java)
                        val steps_total =
                            characterSnapshot.child("steps_total").getValue(Int::class.java)
                        val level = characterSnapshot.child("level").getValue(Int::class.java)

                        CharacterData(
                            name ?: "default",
                            steps_total = steps_total ?: 1000,
                            steps_current = steps_current ?: 0,
                            level = level ?: 0
                        )

                    }


                val res = UserData(
                    studentID ?: "default",
                    steps_current = steps_current ?: 0,
                    steps_total = steps_total ?: 0,
                    characterIndex = characterIndex ?: 0,
                    characterList = characterList
                )
                onSuccess(res)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 가져오기 실패
                Toast.makeText(activity, "읽기 실패", Toast.LENGTH_SHORT).show()
                onFailure()
            }
        })
    }
}