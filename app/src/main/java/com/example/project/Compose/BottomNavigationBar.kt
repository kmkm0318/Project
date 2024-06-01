package com.example.project.Compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(containerColor = Color.White) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            val selectedColor =
                if(currentRoute == navItem.route) colorResource(id = R.color.kumiddlegreen)
                else colorResource(id = R.color.kudarkgreen)

            NavigationBarItem(selected = false,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(Routes.Map.route) {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = navItem.icon,
                        contentDescription = navItem.title,
                        tint = selectedColor
                    )
                },
                label={
                    Text(text = navItem.title)
                }
            )
        }
    }
}

