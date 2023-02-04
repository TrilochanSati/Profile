package com.windapp.profile.ui.add_block_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.windapp.profile.R
import com.windapp.profile.util.UiEvent
import com.windapp.usageoverview.util.Routes

@Composable
fun AddBlockScheduleScreen(
    onNavigate: (UiEvent.Navigate)->Unit,
    viewModel: AddBlockScheduleViewModel= hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
            .wrapContentSize(Alignment.Center)
    ) {

        Button(onClick = {
            onNavigate(UiEvent.Navigate(Routes.DAILY_LIMIT+"?packageName=${viewModel.packageName}"+"?id=${-1}"+"?isUpdate=${false}"))

        }){
            Text("Usage Limit", color = Color.Red)
        }

        Button(onClick = {
            onNavigate(UiEvent.Navigate(Routes.SPECIFIC_TIME_INTERVALS+"?packageName=${viewModel.packageName}"+"?id=${-1}"+"?isUpdate=${false}"))

        }){
            Text("Specific time Intervals", color = Color.Red)
        }

    }
}