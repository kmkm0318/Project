package com.example.project.Screen

import Routes
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.colorResource
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
import com.example.project.Function.ShowFriendOnMap
import com.example.project.Function.loadLanguage
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(navController: NavHostController) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
    val authManager = AuthManager(activity)

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

    val textColor = colorResource(id = R.color.kudarkgreen)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.kudarkgreen),
        unfocusedBorderColor = colorResource(id = R.color.kumiddlegreen),
        cursorColor = colorResource(id = R.color.kudarkgreen),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.kumiddlegreen),
        disabledContentColor = Color.White
    )

    navViewModel.language.value = loadLanguage(context)

    fun loginSuccess() {
        authManager.readFromDatabase(navViewModel.userData, {
            navViewModel.userData = it
            navViewModel.loginStatus.value = true
            authManager.startValueChangeListener({ lat, lng ->
                navViewModel.userData.friendLocationLat = lat
                navViewModel.userData.friendLocationLng = lng
                navViewModel.receiveFriendLocation.value = true
                ShowFriendOnMap(navViewModel, navViewModel.userData.transactingfriendname,lat, lng)
            })
            navController.navigate(Routes.Main.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }, {

        })
    }

    LaunchedEffect(key1 = Unit) {
        val auth = Firebase.auth
        val user = auth.currentUser

        if(user!=null){
            loginSuccess()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 200.dp, bottom = 20.dp),
                    color = textColor,
                    fontFamily = fontFamily
                )
                Text(
                    text = "Campus KUMpass",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 50.dp),
                    color = textColor,
                    fontFamily = fontFamily
                )
                val emailID = when (navViewModel.language.value) {
                    "kr" -> "이메일 아이디"
                    else -> "Email ID"
                }
                OutlinedTextField(
                    colors = textFieldColors,
                    modifier = Modifier.padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = userID,
                    onValueChange = { userID = it },
                    label = {
                        Text(
                            text = emailID,
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
                val passWord = when (navViewModel.language.value) {
                    "kr" -> "패스워드"
                    else -> "PassWord"
                }
                OutlinedTextField(
                    colors = textFieldColors,
                    modifier = Modifier.padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = userPasswd,
                    onValueChange = { userPasswd = it },
                    label = {
                        Text(
                            text = passWord,
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
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
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
                        val login = when (navViewModel.language.value) {
                            "kr" -> "로그인"
                            else -> "Login"
                        }
                        Text(
                            text = login,
                            fontFamily = fontFamily,
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.size(width = 40.dp, height = 0.dp))
                    Button(
                        colors = buttonColor,
                        onClick = { navController.navigate(Routes.Register.route) }) {
                        val register = when (navViewModel.language.value) {
                            "kr" -> "회원가입"
                            else -> "Register"
                        }
                        Text(
                            text = register,
                            fontFamily = fontFamily,
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                }
            }
        }
    }


}