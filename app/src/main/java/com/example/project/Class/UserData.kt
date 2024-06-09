package com.example.project.Class

data class UserData(
    var studentID: String = "default",
    var steps_current: Int = 0,
    var steps_total: Int = 0,
    var prev_steps_total: Int = 0,
    var characterIndex: Int = 0,
    var locationNowLat: Double = 0.0,
    var locationNowLng: Double = 0.0,
    var friendLocationLat: Double = 0.0,
    var friendLocationLng: Double = 0.0,
    var characterList: List<CharacterData> = listOf(),
    var friendList: List<FriendData>? = listOf(),
    var transactingfriendname: String = "default",
    var lastQuizDate: String = ""
) {
    constructor() : this(
        "default", 0, 0, 0, 0,
        0.0, 0.0, 0.0, 0.0, listOf(), listOf()
    )
}