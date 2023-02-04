package com.windapp.profile.ui.home

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
import com.windapp.profile.R
import com.windapp.profile.util.UiEvent
import com.windapp.usageoverview.util.Routes

@Composable
fun HomeScreen(
    onNavigate:(UiEvent.Navigate)->Unit
) {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
            .wrapContentSize(Alignment.Center)
    ) {
        
        Button(onClick = {
            onNavigate(UiEvent.Navigate(Routes.ALL_APPS))

        }){
            Text("Block Apps", color = Color.Red)
        }
        
    }
}