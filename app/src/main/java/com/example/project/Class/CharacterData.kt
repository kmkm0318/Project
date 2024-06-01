package com.example.project.Class

data class CharacterData(
    var name:String = "default",
    var level:Int = 0,
    var steps_current:Int = 0,
    var steps_total:Int = 15000
    ){
    constructor():this("default", 0, 0, 15000)
}