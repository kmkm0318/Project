package com.example.project.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R



@Composable
fun KupetScreen() {
    Scaffold (topBar = {  })
    {contentPadding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "캐릭터 이름 넣으시오", //추후 변수처리
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
                    .padding(start = 40.dp, end = 40.dp, top = 12.dp, bottom = 12.dp)
            )
            //걸음 수에 따라 이미지 바뀌게 구현 -> 이미지 바뀔 때 이펙트(??),
            // 진화에 근접했을 때 알림보내기(진화까지 약 30걸음 남았을 때?)
//            Image(
//                painter = painterResource(id = R.drawable.kulong5),
//                contentDescription = "쿠롱이5단계",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 360.dp, height = 360.dp)
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(12))
//                    .border(
//                        width = 6.dp,
//                        color = colorResource(id = R.color.kudarkgreen),
//                        shape = RoundedCornerShape(12)
//                    )
//            )
            
            Text(text = "이곳에 캐릭터 그림 넣기")

            LinearProgressIndicator(modifier = Modifier
                .padding(6.dp),
                color = colorResource(id = R.color.kudarkgreen),
                trackColor = colorResource(id = R.color.kulightgreen),
                progress = 0.8F//추후 변수처리
            )
            Text(text = "(14488/15000)",//추후 변수처리
                color = colorResource(id = R.color.kudarkgreen),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(bottom=16.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Text(text = "진화까지 남은 발걸음",
                    color = colorResource(id = R.color.kudarkgreen),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "512",//추후 변수처리
                    color = colorResource(id = R.color.kudarkgreen),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp)
            }
            Text(text = "캐릭터 설명 넣기",//추후 변수처리
                color = colorResource(id = R.color.kudarkgreen),
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                modifier = Modifier.padding(top= 32.dp, start= 16.dp, end= 16.dp),
                textAlign = TextAlign.Center
            )
        }

    }

}