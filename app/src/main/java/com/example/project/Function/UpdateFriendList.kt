package com.example.project.Function

import com.example.project.Class.CharacterData
import com.example.project.Class.FriendData
import com.example.project.Class.UserData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

fun UpdateFriendList(userData: UserData, onSuccess: (UserData) -> Unit, onFailure: () -> Unit) {
    val database = Firebase.database
    val reference = database.getReference("users")

    val newList = mutableListOf<FriendData>()

    userData.friendList?.let { list ->
        list.forEach { data ->
            val friendRef = reference.child(data.studentID)



            friendRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val index = snapshot.child("characterIndex").getValue(Int::class.java)
                    val characterRef = friendRef.child(index.toString())
                    characterRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val name = snapshot.child("name").getValue(String::class.java)
                            val level = snapshot.child("level").getValue(Int::class.java)
                            val characterData = CharacterData(name ?: "둘리", level = level ?: 1)
                            newList.add(FriendData(data.name, data.studentID, characterData))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        }
    }
}