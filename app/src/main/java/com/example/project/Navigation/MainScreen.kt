package com.example.project.Navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project.Class.NavViewModel
import com.example.project.Class.StepCountViewModel
import com.example.project.Compose.BottomNavigationBar
import com.example.project.Screen.LoginScreen
import com.example.project.Screen.Register

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()

    val context = LocalContext.current
    val viewModel = remember {
        StepCountViewModel(context)
    }


    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        val navViewModel: NavViewModel =
            viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)


        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text(text = stringResource(id = R.string.title)) }
//                )
//            },
            bottomBar = {
                if (navViewModel.loginStatus.value)
                    BottomNavigationBar(navController)
            }
        ) { contentPadding ->

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    //.background(color = Color(235, 255, 235))
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Login.route
                ) {
                    composable(Routes.Login.route) {
                        LoginScreen(navController)
                    }

                    composable(Routes.Register.route) {
                        Register(navController)
                    }

                    MainNavGraph(navController, viewModel, navViewModel)

                }
            }
        }
    }
}