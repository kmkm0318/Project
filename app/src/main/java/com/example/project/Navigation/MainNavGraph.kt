package com.example.project.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.project.Class.Routes
import com.example.project.Screen.Menu
import com.example.project.Screen.Contacts
import com.example.project.Screen.Favorites
import com.example.project.Screen.Home


fun NavGraphBuilder.MainNavGraph(navController: NavHostController){
    navigation(startDestination = "Home", route="Main"){

        composable(route = Routes.Menu.route) {
            Menu()
        }

        composable(route = Routes.Home.route) {
            Home()
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