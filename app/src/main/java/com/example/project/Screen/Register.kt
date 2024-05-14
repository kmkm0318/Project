package com.example.project.Screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes
import com.example.project.Class.UserData
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R

@Composable
fun Register(navController: NavHostController, authManager: AuthManager, activity: Activity) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var userID by remember {
        mutableStateOf(navViewModel.userID)
    }
    var userPasswd by remember {
        mutableStateOf(navViewModel.userPasswd)
    }
    var studentID by remember {
        mutableStateOf(navViewModel.studentID)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("RegisterViewModel", navViewModel.toString())
        Text(
            text = "Register",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )



        OutlinedTextField(
            value = userID ?: "",
            onValueChange = { userID = it },
            label = { Text(stringResource(id = R.string.emailID)) },
            // 다음 텍스트 필드로 이동할 수 있도록 설정
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            // Enter 키 이벤트 처리
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

        OutlinedTextField(
            value = userPasswd ?: "",
            onValueChange = { userPasswd = it },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            // Enter 키 이벤트 처리
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

        OutlinedTextField(
            value = studentID ?: "",
            onValueChange = { studentID = it },
            label = { Text(stringResource(id = R.string.studentID)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            // Enter 키 이벤트 처리
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }
            )
        )

        Button(onClick = {
            if (userID.isNullOrEmpty() || userPasswd.isNullOrEmpty() || studentID.isNullOrEmpty()) {
                showNotification(activity, "Not all Text Fields are Filled")
            } else {
                authManager.signUpWithEmail(userID!!,
                    userPasswd!!,
                    studentID!!,
                    onSuccess = {
                        val userData = UserData(studentID!!)
                        authManager.writeToDatabase(
                            userData,
                            { navController.navigate(Routes.Login.route) },
                            {})
                    },
                    onFailure = {

                    })
            }


        }) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}