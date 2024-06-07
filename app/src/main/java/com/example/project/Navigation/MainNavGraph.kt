package com.example.project.Navigation

import Routes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.project.Class.NavViewModel
import com.example.project.Class.StationViewModel
import com.example.project.Class.StepCountViewModel
import com.example.project.Screen.CharacterDictionaryScreen
import com.example.project.Screen.ChildrensGrandPark
import com.example.project.Screen.CorrectAns
import com.example.project.Screen.DocumentationScreen
import com.example.project.Screen.FriendDetailScreen
import com.example.project.Screen.FriendScreen
import com.example.project.Screen.KonkukUnivStation
import com.example.project.Screen.KueuiStation
import com.example.project.Screen.KupetScreen
import com.example.project.Screen.MapScreen
import com.example.project.Screen.MenuScreen
import com.example.project.Screen.MetroArrivalScreen
import com.example.project.Screen.QuizScreen
import com.example.project.Screen.WrongAns


fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    viewModel: StepCountViewModel,
    navViewModel: NavViewModel
) {

    navigation(startDestination = "Map", route = "Main") {
        composable(route = Routes.Menu.route) { it ->
            MenuScreen(navController)
        }
        
        composable(route = Routes.Documentation.route){
            DocumentationScreen(navController = navController)
        }

        composable(route = Routes.CharacterDictionary.route){
            CharacterDictionaryScreen(navController = navController)
        }

        composable(route = Routes.Map.route) {
            MapScreen(navController)
        }

        composable(route = Routes.Kupet.route) {
            KupetScreen(navController, viewModel)
        }

        composable(route = Routes.Friend.route) {
            FriendScreen(navController)
        }

        composable(route = Routes.FriendDetail.route) {
            FriendDetailScreen(navController)
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
        
        composable(route = Routes.Metro.route){
            MetroArrivalScreen(navController, stationViewModel = StationViewModel())
        }

        composable(route = Routes.KonkukUnivStation.route){
            KonkukUnivStation(navController, stationViewModel = StationViewModel())
        }

        composable(route = Routes.ChildrensGrandPark.route){
            ChildrensGrandPark(navController, stationViewModel = StationViewModel())
        }

        composable(route = Routes.KueuiStation.route){
            KueuiStation(navController, stationViewModel = StationViewModel())
        }

    }
}