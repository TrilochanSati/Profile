package com.windapp.profile.ui.add_block_schedule

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBlockScheduleViewModel @Inject
 constructor   (
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var packageName by mutableStateOf("")
        private set
    init {
        packageName=savedStateHandle.get<String>("packageName")!!
        Log.d("dli",packageName)


    }
}