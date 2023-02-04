package com.windapp.profile.ui.specific_time_intervals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.profile.data.ProfileRepository
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SpecificTimeIntervalViewModel @Inject constructor(
    private val repository: ProfileRepository,
    savedStateHandle: SavedStateHandle
)

    : ViewModel() {

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

    var timeStart by mutableStateOf("12:00 AM")
        private set

    var timeEnd by mutableStateOf("1:00 AM")
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

    fun onEvent(event: SpecificTimeIntervalEvent){
        when(event){
            is SpecificTimeIntervalEvent.OnAppLaunchSwitchChange ->{
                appLaunchSwitch=event.flag
            }
            is SpecificTimeIntervalEvent.OnNotificationSwitchChange ->{
                notificationSwitch=event.flag
            }
            is SpecificTimeIntervalEvent.OnDaysClick ->{

                when(event.day){
                    DayOfWeek.MONDAY ->if(onDays.get(DayOfWeek.MONDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.MONDAY.ordinal,'M') else onDays.set(DayOfWeek.MONDAY.ordinal,'X')
                    DayOfWeek.TUESDAY ->if(onDays.get(DayOfWeek.TUESDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.TUESDAY.ordinal,'T') else onDays.set(DayOfWeek.TUESDAY.ordinal,'X')
                    DayOfWeek.WEDNESDAY ->if(onDays.get(DayOfWeek.WEDNESDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.WEDNESDAY.ordinal,'W') else onDays.set(DayOfWeek.WEDNESDAY.ordinal,'X')
                    DayOfWeek.THURSDAY ->if(onDays.get(DayOfWeek.THURSDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.THURSDAY.ordinal,'H') else onDays.set(DayOfWeek.THURSDAY.ordinal,'X')
                    DayOfWeek.FRIDAY ->if(onDays.get(DayOfWeek.FRIDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.FRIDAY.ordinal,'F') else onDays.set(DayOfWeek.FRIDAY.ordinal,'X')
                    DayOfWeek.SATURDAY ->if(onDays.get(DayOfWeek.SATURDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.SATURDAY.ordinal,'S') else onDays.set(DayOfWeek.SATURDAY.ordinal,'X')
                    DayOfWeek.SUNDAY ->if(onDays.get(DayOfWeek.SUNDAY.ordinal).equals('X'))onDays.set(
                        DayOfWeek.SUNDAY.ordinal,'U') else onDays.set(DayOfWeek.SUNDAY.ordinal,'X')


                }
                Log.d("dli",onDays.toString())



            }
            is SpecificTimeIntervalEvent.OnAddTimeRangeClick ->{
                timeStart=event.timeStart
                timeEnd=event.timeEnd
            }
            is SpecificTimeIntervalEvent.OnMotiTextChange ->{
                motiText=event.text
            }

            is SpecificTimeIntervalEvent.OnSaveClick ->{
                viewModelScope.launch {
                    repository.insertSpecificTimeInterval(
                        SpecificTimeIntervals(
                            packageName = packageName,
                            blockApp =    appLaunchSwitch,
                            blockNotification =   notificationSwitch,
                            onDays =        onDays.toString(),
                            timeStart =englishTime(timeStart),
                            timeEnd=englishTime(timeEnd),
                            motiText =      motiText

                        )
                    )
                }
                sendUiEvent(UiEvent.PopBackStack)

            }

            is SpecificTimeIntervalEvent.OnUpdateClick ->{
                viewModelScope.launch {
                    repository.updateSpecificTimeIntervalById(id = id,
                        blockApp =    appLaunchSwitch,
                        blockNotification =   notificationSwitch,
                        onDays =        onDays.toString(),
                        timeStart =englishTime( timeStart)    ,
                        timeEnd= englishTime( timeEnd),
                        motiText =      motiText)
                }
                sendUiEvent(UiEvent.PopBackStack)

            }

        }
    }

    private fun setDefault(id:Int){
        viewModelScope.launch{
            var specificTimeIntervals=   repository.getSpecificTimeIntervalById(id)
            appLaunchSwitch=specificTimeIntervals.blockApp
            notificationSwitch=specificTimeIntervals.blockNotification
            onDays=java.lang.StringBuilder(specificTimeIntervals.onDays)
            timeStart=normalTime(specificTimeIntervals.timeStart)
            timeEnd=normalTime(specificTimeIntervals.timeEnd)
            motiText=specificTimeIntervals.motiText
            Log.d("dli","onDays "+onDays.toString())

        }

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun englishTime(input: String): String {

        // Format of the date defined in the input String
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm aa")

        // Change the pattern into 24 hour format
        val format: DateFormat = SimpleDateFormat("HH:mm")
        var time: Date? = null
        var output = ""

        // Converting the input String to Date
        time = dateFormat.parse(input)

        // Changing the format of date
        // and storing it in
        // String
        output = format.format(time)
        return output
    }

    fun normalTime(input: String): String {

        // Format of the date defined in the input String
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm")

        // Change the pattern into 24 hour format
        val format: DateFormat = SimpleDateFormat("hh:mm aa")
        var time: Date? = null
        var output = ""

        // Converting the input String to Date
        time = dateFormat.parse(input)

        // Changing the format of date
        // and storing it in
        // String
        output = format.format(time)
        return output
    }

}