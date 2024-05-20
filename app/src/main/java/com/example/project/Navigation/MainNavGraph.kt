package com.example.project.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.project.Class.Routes
import com.example.project.Screen.CorrectAns
import com.example.project.Screen.FriendScreen
import com.example.project.Screen.KupetScreen
import com.example.project.Screen.MapScreen
import com.example.project.Screen.MenuScreen
import com.example.project.Screen.QuizScreen
import com.example.project.Screen.WrongAns


fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
) {
    navigation(startDestination = "Map", route = "Main") {
        composable(route = Routes.Menu.route) { it ->
            MenuScreen(navController)
        }

        composable(route = Routes.Map.route) {
            MapScreen()
        }

        composable(route = Routes.Kupet.route) {
            KupetScreen()
        }

        composable(route = Routes.Friend.route) {
            FriendScreen()
        }

        composable(route = Routes.Quiz.route) {
            QuizScreen(navController)
        }
        composable(route = Routes.CorrectAns.route) {
            CorrectAns(navController)
        }

        composable(route = Routes.WrongAns.route) {
            WrongAns(navController)
        }
    }
}