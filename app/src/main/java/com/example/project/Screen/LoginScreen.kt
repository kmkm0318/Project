package com.example.project.Screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.Routes
import com.example.project.Function.loadLanguage
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavHostController) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
    val authManager = AuthManager(activity)

    navViewModel.language = loadLanguage(context)
    Log.i("language : ", navViewModel.language)

    var userID by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val textColor = Color(25, 200, 25)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Green,
        unfocusedBorderColor = Color(25, 200, 25),
        cursorColor = Color(25, 200, 25),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = Color(25, 200, 25),
        contentColor = Color.White,
        disabledContainerColor = Color.Green,
        disabledContentColor = Color.Green
    )

    fun loginSuccess() {
        authManager.readFromDatabase({
            navViewModel.userData = it
        }, {

        })
        navViewModel.loginStatus.value = true

        navController.navigate(Routes.Main.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(Unit) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.i("Login", "already Logined")
            loginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 100.dp, bottom = 40.dp),
            color = textColor,
            fontFamily = fontFamily
        )

        OutlinedTextField(
            colors = textFieldColors,
            modifier = Modifier.padding(bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            value = userID,
            onValueChange = { userID = it },
            label = {
                Text(
                    stringResource(id = R.string.emailID),
                    color = textColor,
                    fontFamily = fontFamily
                )
            },
            // 다음 텍스트 필드로 이동할 수 있도록 설정
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            // Enter 키 이벤트 처리
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

        OutlinedTextField(
            colors = textFieldColors,
            modifier = Modifier.padding(bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            value = userPasswd,
            onValueChange = { userPasswd = it },
            label = {
                Text(
                    stringResource(id = R.string.password),
                    color = textColor,
                    fontFamily = fontFamily
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            // Enter 키 이벤트 처리
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(colors = buttonColor, onClick = {
                if (userID.isNullOrEmpty() || userPasswd.isNullOrEmpty()) {
                    showNotification(activity, "Not all Text Fields are Filled")
                } else {
                    authManager.signInWithEmail(userID, userPasswd, onSuccess = {
                        loginSuccess()
                    }, onFailure = {})
                }


            }) {
                Text(text = stringResource(id = R.string.login), fontFamily = fontFamily)
            }
            Spacer(modifier = Modifier.size(width = 90.dp, height = 0.dp))
            Button(
                colors = buttonColor,
                onClick = { navController.navigate(Routes.Register.route) }) {
                Text(text = stringResource(id = R.string.register), fontFamily = fontFamily)
            }
        }


    }
}