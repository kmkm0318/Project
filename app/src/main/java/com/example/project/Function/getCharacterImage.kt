package com.example.project.Function

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.project.Class.CharacterData

@Composable
fun getCharacterImage(characterData: CharacterData) {
    val characterName = when {
        characterData.name == "코코" -> "c"
        characterData.name == "둘리" -> "d"
        characterData.name == "프로리" -> "f"
        characterData.name == "루나" -> "l"
        characterData.name == "모모" -> "m"
        else -> "d"
    }
    val characterLevel = characterData.level.toString()

    val fileName = characterName + characterLevel
    val fileID = LocalContext.current.resources.getIdentifier(
        fileName,
        "drawable",
        LocalContext.current.packageName
    )

    Image(painter = painterResource(id = fileID), contentDescription = "", Modifier.fillMaxSize())
}