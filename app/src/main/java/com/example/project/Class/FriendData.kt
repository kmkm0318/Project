package com.example.project.Class

data class FriendData(
    var name: String = "default",
    var studentID: String = "default",
    var characterData: CharacterData = CharacterData()
){
    constructor():this("default", "default", CharacterData())
}