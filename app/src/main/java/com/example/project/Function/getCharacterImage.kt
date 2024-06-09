package com.example.project.Function

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.project.Class.CharacterData
import com.example.project.R

@Composable
fun getCharacterImage(characterData: CharacterData, modifier: Modifier = Modifier) {
    val characterName = characterData.name
    val characterLevel = characterData.level.toString()

    val fileName = characterName + characterLevel
    val fileID = LocalContext.current.resources.getIdentifier(
        fileName,
        "drawable",
        LocalContext.current.packageName
    )

    Image(painter = painterResource(id = fileID), contentDescription = "", modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth)
}

fun getCharacterImageAsInt(context: Context, characterData: CharacterData) : Int {
    val characterName = characterData.name
    val characterLevel = characterData.level.toString()

    val fileName = characterName + characterLevel

    return context.resources.getIdentifier(fileName, "drawable", context.packageName)
}