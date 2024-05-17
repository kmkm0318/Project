package com.example.project.Compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem (val title :String, val selectIcon: ImageVector, val onSelectedIcon :ImageVector, val route:String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Menu",
            selectIcon = Icons.Default.Menu,
            onSelectedIcon = Icons.Outlined.Menu,
            route = "Menu"
        ),
//        BarItem(
//            title = "Map",
//            selectIcon = Icons.Default.Home,
//            onSelectedIcon = Icons.Outlined.Home,
//            route = "Map"
//        ),
        BarItem(
            title = "Kupet",
            selectIcon = Icons.Default.Favorite,
            onSelectedIcon = Icons.Outlined.Favorite,
            route = "Kupet"
        ),
        BarItem(
            title = "Quiz",
            selectIcon = Icons.Default.Check,
            onSelectedIcon = Icons.Outlined.Check,
            route = "Quiz"
        ),
        BarItem(
            title = "Friend",
            selectIcon = Icons.Default.Person,
            onSelectedIcon = Icons.Outlined.Person,
            route = "Friend"
        )

    )
}