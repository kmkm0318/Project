package com.example.project.Screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.Class.AuthManager
import com.example.project.Class.NavViewModel
import com.example.project.Class.StepCountViewModel
import com.example.project.Class.UserData
import com.example.project.Compose.TopBar
import com.example.project.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun KupetScreen(
    navController: NavHostController,
    viewModel: StepCountViewModel,
    navViewModel: NavViewModel
) {
    Scaffold(
        topBar = { TopBar(navController = navController) }
    ) { contentPadding ->
        KupetScreenContent(viewModel, navViewModel, contentPadding, navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun KupetScreenContent(
    viewModel: StepCountViewModel,
    navViewModel: NavViewModel,
    contentPadding: PaddingValues,
    navController: NavHostController
) {
    val activity = LocalContext.current as Activity
    val authManager = AuthManager(activity)
    authManager.readFromDatabase(navViewModel.userData,{navViewModel.userData = it}, {})

    var userData by remember {
        mutableStateOf(UserData())
    }

    userData = navViewModel.userData

    var kupetIndex by remember {
        mutableIntStateOf(0)
    }
    var selectedKupet by remember {
        mutableIntStateOf(0)
    }

    val petList: List<Int> = listOf(
        R.drawable.duri0, //0
        R.drawable.duri1,
        R.drawable.duri2,
        R.drawable.duri3,
        R.drawable.frole0, //4
        R.drawable.frole1,
        R.drawable.frole2,
        R.drawable.frole3,
        R.drawable.momo0, // 8
        R.drawable.momo1,
        R.drawable.momo2,
        R.drawable.momo3
    )

    val petNameList: List<String> = listOf(
        "두리",
        "프롤",
        "모모",
    )

    val petCommentList: List<String> = listOf(
        "두리 설명",
        "프롤 설명",
        "모모 설명",
        "다른 쿠펫을 키워보세요"
    )

    var leftOverStep by remember {
        mutableIntStateOf(0)
    }


    val context = LocalContext.current

    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)

    var showDialog by remember {
        mutableStateOf(false)
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted)
                showDialog = true
        }

    val stepCount by viewModel.stepCountFlow.collectAsState(initial = 0)

    var step by remember {
        mutableIntStateOf(userData.steps_total)
    }





    if (userData.steps_current == 0){
        userData.steps_current = stepCount
        navViewModel.userData = userData
        authManager.writeToDatabase(navViewModel.userData)
    }else if(userData.steps_current > stepCount && stepCount != 0){ // step_sensor 초기화 한 경우
        userData.steps_current = stepCount
        userData.prev_steps_total = userData.steps_total
        navViewModel.userData = userData
        authManager.writeToDatabase(navViewModel.userData)
    }


    if (stepCount != 0) {
        if (userData.prev_steps_total == 0){
            step = stepCount - userData.steps_current
        }else{
            step = stepCount - userData.steps_current + userData.prev_steps_total
        }
    }


    if (step > 45000) {
        kupetIndex = 4 * selectedKupet + 3
    } else {
        kupetIndex = (step / 15000) + 4 * selectedKupet
    }

    leftOverStep = 15000 - step%15000

    LaunchedEffect(key1 = permissionState) {
        if (!permissionState.status.isGranted && !permissionState.status.shouldShowRationale) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }

    if (showDialog) {
        if (permissionState.status.shouldShowRationale) {
            ShowCallPermissionRationale(
                onConfirm = {
                    showDialog = false
                    requestPermissionLauncher.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)
                },
                onDismiss = {
                    showDialog = false
                })
        } else {
            ShowCallPermissionRationale(
                onConfirm = {
                    showDialog = false
                    //requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                },
                onDismiss = {
                    showDialog = false
                })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        if (!permissionState.status.isGranted) {
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text(text = "kupet 시작하기")
            }

        } else {
            if (!viewModel.hasSensor()) {
                Text(text = "이 기기에서는 kupet을 이용할 수 없습니다")
            } else {
                Scaffold(topBar = { })
                { contentPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = petNameList[selectedKupet], //추후 선택한 캐릭터로 변수처리
                            color = colorResource(id = R.color.kudarkgreen),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .border(
                                    width = 3.dp,
                                    color = colorResource(id = R.color.kudarkgreen),
                                    shape = RoundedCornerShape(12)
                                )
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                )
                        )
                        //걸음 수에 따라 이미지 바뀌게 구현 -> 이미지 바뀔 때 이펙트(??),
                        // 진화에 근접했을 때 알림보내기(진화까지 약 30걸음 남았을 때?)
                        Image(
                            painter = painterResource(id = petList[kupetIndex]),
                            contentDescription = "두리0단계",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 360.dp, height = 360.dp)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12))
                                .border(
                                    width = 6.dp,
                                    color = colorResource(id = R.color.kudarkgreen),
                                    shape = RoundedCornerShape(12)
                                )
                        )

                        LinearProgressIndicator(
                            modifier = Modifier
                                .padding(6.dp),
                            color = colorResource(id = R.color.kudarkgreen),
                            trackColor = colorResource(id = R.color.kulightgreen),
                            progress = ((step).toFloat() / 15000)//추후 변수처리
                        )
                        Text(
                            text = "(${step}/15000)",//추후 변수처리
                            color = colorResource(id = R.color.kudarkgreen),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "진화까지 남은 발걸음",
                                color = colorResource(id = R.color.kudarkgreen),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "$leftOverStep",//추후 변수처리
                                color = colorResource(id = R.color.kudarkgreen),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 24.sp
                            )
                        }
                        Text(
                            text = petCommentList[selectedKupet],//추후 변수처리
                            color = colorResource(id = R.color.kudarkgreen),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(
                                top = 32.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }

    userData.steps_total = step
    navViewModel.userData = userData
    authManager.writeToDatabase(navViewModel.userData)

}

@Composable
fun ShowCallPermissionRationale(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "권한 요청")
        },
        text = {
            Text(text = "Kupet을 이용하기 위해서는 신체 활동 추적 권한이 필요합니다")
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "권한 승인")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "거절")
            }
        }
    )
}