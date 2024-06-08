package com.example.project.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.Class.SearchViewModel
import com.example.project.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.play.integrity.internal.s
import com.google.maps.android.compose.CameraPositionState

@Composable
fun SearchRecommend(
    searchInput: String,
    searchViewModel: SearchViewModel,
    cameraPositionState: CameraPositionState,
    onSearchCleared: () -> Unit
) {
    val searchResults by searchViewModel.searchResults.collectAsState()

    LaunchedEffect(searchInput) {
        if (searchInput.isNotEmpty()) {
            searchViewModel.searchBuilding(searchInput)
        }
    }

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )

    val buttonColors = ButtonDefaults.textButtonColors(
        containerColor = colorResource(id = R.color.kumiddlegreen),
        contentColor = colorResource(id = R.color.kuhighlightgreen),
        disabledContainerColor = colorResource(id = R.color.kumiddlegreen),
        disabledContentColor = colorResource(id = R.color.white)
    )

    LazyColumn(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(searchResults) { searchres ->
            TextButton(
                onClick = {
                    val latLng = LatLng(searchres.latitude,searchres.longitude)
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                    onSearchCleared()
                },
                modifier = Modifier.padding(vertical = 8.dp).width(240.dp),
                colors = buttonColors
            ) {
                Text(
                    text = searchres.rusult,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }

}