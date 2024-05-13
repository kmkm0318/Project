package com.example.project.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner

@Composable
fun LoginScreen(navController: NavHostController, authManager: AuthManager) {
    val navViewModel: NavViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var userID by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text="Home Screen",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold)

        OutlinedTextField(value = userID,
            onValueChange = {userID =it},
            label = {Text("아이디")}
        )

        OutlinedTextField( value = userPasswd,
            onValueChange = { userPasswd = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {
            authManager.signInWithEmail(userID, userPasswd, onSuccess = {
                navController.navigate(Routes.Welcome.route)
            },
                onFailure = {

                })

        }){
            Text(text = "로그인")
        }

        Button(onClick = {
            authManager.signUpWithEmail(userID, userPasswd, onSuccess = {
                navController.navigate(Routes.Login.route)
            },
                onFailure = {

                })

        }){
            Text(text = "가입")
        }
    }
}