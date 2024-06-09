package com.example.project.Screen

import Routes
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.project.Function.showNotification
import com.example.project.Navigation.LocalNavGraphViewModelStoreOwner
import com.example.project.R

@Composable
fun Register(navController: NavHostController) {
    val navViewModel: NavViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity

    val authManager = AuthManager(activity)

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

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val textColor = colorResource(id = R.color.kusemidarkgreen)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.kumiddlegreen),
        unfocusedBorderColor = colorResource(id = R.color.kusemidarkgreen),
        cursorColor = colorResource(id = R.color.kudarkgreen),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor
    )

    val buttonColor = ButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.kusemidarkgreen),
        disabledContentColor = Color.White
    )

    LazyColumn {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val register = when (navViewModel.language.value) {
                    "kr" -> "가입하기"
                    else -> "Register"
                }
                Text(
                    text = register,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 200.dp, bottom = 40.dp),
                    color = textColor,
                    fontFamily = fontFamily
                )



                OutlinedTextField(
                    colors = textFieldColors,
                    modifier = Modifier.padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = userID ?: "",
                    onValueChange = { userID = it },
                    label = {
                        val emailID = when (navViewModel.language.value) {
                            "kr" -> "이메일 아이디"
                            else -> "Email ID"
                        }
                        Text(
                            text = emailID, color = textColor, fontFamily = fontFamily
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
                    value = userPasswd ?: "",
                    onValueChange = { userPasswd = it },
                    label = {
                        val passWord = when (navViewModel.language.value) {
                            "kr" -> "패스워드"
                            else -> "PassWord"
                        }
                        Text(
                            text = passWord, color = textColor, fontFamily = fontFamily
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                    ),
                    // Enter 키 이벤트 처리
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )

                OutlinedTextField(
                    colors = textFieldColors,
                    modifier = Modifier.padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    value = studentID ?: "",
                    onValueChange = { studentID = it },
                    label = {
                        val studentID = when (navViewModel.language.value) {
                            "kr" -> "학번"
                            else -> "Student ID"
                        }
                        Text(
                            text = studentID, color = textColor, fontFamily = fontFamily
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    // Enter 키 이벤트 처리
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    })
                )

                Button(colors = buttonColor, onClick = {
                    if (userID.isNullOrEmpty() || userPasswd.isNullOrEmpty() || studentID.isNullOrEmpty()) {
                        showNotification(activity, "Not all Text Fields are Filled")
                    } else {
                        authManager.signUpWithEmail(
                            userID!!,
                            userPasswd!!,
                            studentID!!,
                            onSuccess = {
                                navController.navigate(Routes.Login.route)
                            },
                            onFailure = {

                            })
                    }


                }) {
                    val register = when (navViewModel.language.value) {
                        "kr" -> "회원가입"
                        else -> "Register"
                    }
                    Text(
                        text = register,
                        fontFamily = fontFamily,
                        modifier = Modifier.wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }

}