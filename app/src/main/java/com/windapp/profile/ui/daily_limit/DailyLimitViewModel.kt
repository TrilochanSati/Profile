package com.windapp.profile.ui.daily_limit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.profile.data.ProfileRepository
import com.windapp.profile.data.entities.UsageLimitDaily
import com.windapp.profile.util.UiEvent
import com.windapp.usageoverview.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class DailyLimitViewModel @Inject constructor(
    private val repository:ProfileRepository,
    savedStateHandle: SavedStateHandle
)

    :ViewModel() {

        var packageName by mutableStateOf("")
        private set

        var isUpdate by mutableStateOf(false)

        var id by mutableStateOf(-1)

        var appLaunchSwitch by mutableStateOf(false)
        private set

        var notificationSwitch by mutableStateOf(false)
        private set

        var onDays by mutableStateOf<StringBuilder>(java.lang.StringBuilder("XXXXXXX"))
        private set

        var timeLimit by mutableStateOf(30)
        private set

        var motiText by mutableStateOf("काल करे सो आज कर, आज करे सो अब । पल में परलय होएगी, बहुरि करेगा कब ॥")
        private set

        private val _uiEvent= Channel<UiEvent>()
        val uiEvent=_uiEvent.receiveAsFlow()

    init {
        id=savedStateHandle.get<Int>("id")?:-1
        isUpdate=savedStateHandle.get<Boolean>("isUpdate")?:false
        packageName=savedStateHandle.get<String>("packageName")!!
        if(isUpdate){
            setDefault(id)
            Log.d("dli",packageName)

        }


    }

        fun onEvent(event: DailyLimitEvent){
            when(event){
                is DailyLimitEvent.OnAppLaunchSwitchChange ->{
                    appLaunchSwitch=event.flag
                }
                is DailyLimitEvent.OnNotificationSwitchChange ->{
                    notificationSwitch=event.flag
                }
                is DailyLimitEvent.OnDaysClick ->{

                    when(event.day){
                         DayOfWeek.MONDAY ->if(onDays.get(DayOfWeek.MONDAY.ordinal).equals('X'))onDays.set(DayOfWeek.MONDAY.ordinal,'M') else onDays.set(DayOfWeek.MONDAY.ordinal,'X')
                         DayOfWeek.TUESDAY ->if(onDays.get(DayOfWeek.TUESDAY.ordinal).equals('X'))onDays.set(DayOfWeek.TUESDAY.ordinal,'T') else onDays.set(DayOfWeek.TUESDAY.ordinal,'X')
                         DayOfWeek.WEDNESDAY ->if(onDays.get(DayOfWeek.WEDNESDAY.ordinal).equals('X'))onDays.set(DayOfWeek.WEDNESDAY.ordinal,'W') else onDays.set(DayOfWeek.WEDNESDAY.ordinal,'X')
                         DayOfWeek.THURSDAY ->if(onDays.get(DayOfWeek.THURSDAY.ordinal).equals('X'))onDays.set(DayOfWeek.THURSDAY.ordinal,'H') else onDays.set(DayOfWeek.THURSDAY.ordinal,'X')
                         DayOfWeek.FRIDAY ->if(onDays.get(DayOfWeek.FRIDAY.ordinal).equals('X'))onDays.set(DayOfWeek.FRIDAY.ordinal,'F') else onDays.set(DayOfWeek.FRIDAY.ordinal,'X')
                         DayOfWeek.SATURDAY ->if(onDays.get(DayOfWeek.SATURDAY.ordinal).equals('X'))onDays.set(DayOfWeek.SATURDAY.ordinal,'S') else onDays.set(DayOfWeek.SATURDAY.ordinal,'X')
                         DayOfWeek.SUNDAY ->if(onDays.get(DayOfWeek.SUNDAY.ordinal).equals('X'))onDays.set(DayOfWeek.SUNDAY.ordinal,'U') else onDays.set(DayOfWeek.SUNDAY.ordinal,'X')


                    }
                    Log.d("dli",onDays.toString())



                }
                is DailyLimitEvent.OnTimeSpentChange ->{
                    timeLimit=event.time
                }
                is DailyLimitEvent.OnMotiTextChange ->{
                    motiText=event.text
                }
                is DailyLimitEvent.OnSaveAndAddMoreClick ->{
                    viewModelScope.launch {
                        repository.insertUsageLimitDaily(
                        UsageLimitDaily(
                            packageName = packageName,
                            blockApp =    appLaunchSwitch,
                            blockNotification =   notificationSwitch,
                            onDays =        onDays.toString(),
                            timeLimit =     timeLimit,
                            motiText =      motiText

                        )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStack)

                }

                is DailyLimitEvent.OnSaveClick ->{
                    viewModelScope.launch {
                        repository.insertUsageLimitDaily(
                            UsageLimitDaily(
                                packageName = packageName,
                                blockApp =    appLaunchSwitch,
                                blockNotification =   notificationSwitch,
                                onDays =        onDays.toString(),
                                timeLimit =     timeLimit,
                                motiText =      motiText

                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStack)

                }

                is DailyLimitEvent.OnUpdateClick ->{
                    viewModelScope.launch {
                        repository.updateUsageLimitDailyById(id = id,
                            blockApp =    appLaunchSwitch,
                            blockNotification =   notificationSwitch,
                            onDays =        onDays.toString(),
                            timeLimit =     timeLimit,
                            motiText =      motiText)
                    }
                    sendUiEvent(UiEvent.PopBackStack)

                }

            }
        }

    private fun setDefault(id:Int){
        viewModelScope.launch{
         var usageLimitDaily=   repository.getUsageLimitDailyById(id)
            appLaunchSwitch=usageLimitDaily.blockApp
            notificationSwitch=usageLimitDaily.blockNotification
            onDays=java.lang.StringBuilder(usageLimitDaily.onDays)
            timeLimit=usageLimitDaily.timeLimit
            motiText=usageLimitDaily.motiText
            Log.d("dli","onDays "+onDays.toString())

        }

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}