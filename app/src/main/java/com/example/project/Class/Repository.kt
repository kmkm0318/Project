package com.example.project.Class

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Repository(private val table:DatabaseReference) {
    fun UpdateUserData(userData: UserData){
        table.setValue(userData)
    }

    fun readUser(): Flow<List<UserData>> = callbackFlow {
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<UserData>()
                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(UserData::class.java)
                    user?.let { userList.add(user) }
                    Log.w("readUser", "$user")
                }
                trySend(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("readUser", "failed")
            }
        }
        table.addValueEventListener(listener)
        awaitClose{
            table.removeEventListener(listener)
        }
    }
}