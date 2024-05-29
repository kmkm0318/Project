package com.example.project.Compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem (val title :String, val icon: ImageVector, val route:String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Menu",
            icon = Icons.Default.Menu,
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
            icon = Icons.Default.ChildCare,
            route = "Kupet"
        ),
        BarItem(
            title = "SpeedQuiz",
            icon = Icons.Default.Bolt,
            route = "Quiz"
        ),
        BarItem(
            title = "Friend",
            icon = Icons.Default.SupervisorAccount,
            route = "Friend"
        )

    )
}