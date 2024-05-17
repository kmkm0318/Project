package com.example.project.Navigation

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.project.Class.AuthManager
import com.example.project.Class.Routes
import com.example.project.Screen.Contacts
import com.example.project.Screen.Favorites
import com.example.project.Screen.Home
import com.example.project.Screen.Menu


fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    authManager: AuthManager,
    activity: Activity
) {
    navigation(startDestination = "Home", route = "Main") {

        composable(route = Routes.Menu.route) {
            Menu()
        }

        composable(route = Routes.Home.route) {
            Home(navController)
        }

        composable(
            route = Routes.Favorites.route
        ) {
            Favorites()
        }

        composable(route = Routes.Contacts.route) { it ->
            Contacts()
        }
    }
}