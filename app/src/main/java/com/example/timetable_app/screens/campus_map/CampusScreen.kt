package com.example.timetable_app.screens.campus_map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable_app.R.drawable
import com.example.timetable_app.R.string
import com.example.timetable_app.common.composable.ActionToolbar
import com.example.timetable_app.common.ext.toolbarActions
import com.example.timetable_app.model.Campus
import com.example.timetable_app.screens.lectures.LecturesViewModel
import com.example.timetable_app.ui.theme.AppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import com.example.timetable_app.ui.theme.AppTheme as TimetableAppTheme
import com.example.timetable_app.R.drawable as AppIcon
import com.example.timetable_app.R.string as AppText

// ...

@Composable
fun CampusScreen(
    openScreen: (String) -> Unit,
    viewModel: CampusScreenViewModel = hiltViewModel()
) {
    val campus = viewModel.campus
    val cameraPositionState = rememberCameraPositionState {

        //no matter how hard i try, the query just doesn't work
        position = CameraPosition.fromLatLngZoom(LatLng(
            campus?.lat ?: 52.675183922507564,
            campus?.lng ?: -8.648493787771454
        ), 15f)
    }
    CampusScreenContent(position = cameraPositionState,
        openScreen = openScreen)
}

@Composable
fun CampusScreenContent(position: CameraPositionState,
                        openScreen: (String) -> Unit){
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = position
        )

    }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun CampusScreenPreview() {
    TimetableAppTheme {
        CampusScreenContent(position = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(52.675183922507564, -8.648493787771454), 15f)
        },
            {})
    }
}