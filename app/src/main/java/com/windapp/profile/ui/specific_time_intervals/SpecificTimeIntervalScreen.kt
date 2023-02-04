package com.windapp.profile.ui.specific_time_intervals

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.windapp.profile.R
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.ui.daily_limit.DailyLimitEvent
import com.windapp.profile.ui.daily_limit.DailyLimitViewModel
import com.windapp.profile.util.UiEvent
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SpecificTimeIntervalScreen(
    onPopBackStack:()->Unit,
    viewModel: SpecificTimeIntervalViewModel = hiltViewModel()

) {

    var pickedTimeStart by remember {
        mutableStateOf(LocalTime.now())
    }

    val formattedTimeStart by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(pickedTimeStart)
        }
    }

    var pickedTimeEnd by remember {
        mutableStateOf(LocalTime.now())
    }

    val formattedTimeEnd by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(pickedTimeEnd)
        }
    }

    val timeStartDialogState= rememberMaterialDialogState()
    val timeEndDialogState= rememberMaterialDialogState()

    var mondayColor by           remember {
        mutableStateOf(  Color.Gray)

    }
    var tuesdayColor by           remember {
        mutableStateOf(  Color.Gray)
    }
    var wednesDayColor by           remember {
        mutableStateOf(  Color.Gray)
    }
    var thrusdayColor by           remember {
        mutableStateOf(  Color.Gray)
    }
    var fridayColor by           remember {
        mutableStateOf(  Color.Gray)
    }

    var saturdayColor by           remember {
        mutableStateOf(  Color.Gray)
    }

    var sundayColor by           remember {
        mutableStateOf(  Color.Gray)
    }

    if(viewModel.onDays[DayOfWeek.MONDAY.ordinal]=='M'){
        mondayColor=Color.Green
    }
    else{
        mondayColor=Color.Gray

    }

    if(viewModel.onDays[DayOfWeek.TUESDAY.ordinal]=='T'){
        tuesdayColor=Color.Green
    }
    else{
        tuesdayColor=Color.Gray

    }


    if(viewModel.onDays[DayOfWeek.WEDNESDAY.ordinal]=='W'){
        wednesDayColor=Color.Green
    }
    else{
        wednesDayColor=Color.Gray

    }

    if(viewModel.onDays[DayOfWeek.THURSDAY.ordinal]=='H'){
        thrusdayColor=Color.Green
    }
    else{
        thrusdayColor=Color.Gray

    }

    if(viewModel.onDays[DayOfWeek.FRIDAY.ordinal]=='F'){
        fridayColor=Color.Green
    }
    else{
        fridayColor=Color.Gray

    }

    if(viewModel.onDays[DayOfWeek.SATURDAY.ordinal]=='S'){
        saturdayColor=Color.Green
    }
    else{
        saturdayColor=Color.Gray

    }

    if(viewModel.onDays[DayOfWeek.SUNDAY.ordinal]=='U'){
        sundayColor=Color.Green
    }
    else{
        sundayColor=Color.Gray

    }



    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
            .wrapContentSize(Alignment.Center)
    ) {


        Text("I Want to Block", color = Color.Red)
        Row {
            Icon(imageVector = Icons.Default.AccountBox , contentDescription = "App Launch Icon")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "App Launch")
            Spacer(modifier = Modifier.width(48.dp))
            Switch(checked = viewModel.appLaunchSwitch, onCheckedChange = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnAppLaunchSwitchChange(it))
            })

            Spacer(modifier = Modifier.height(16.dp))




        }

        Row {
            Icon(imageVector = Icons.Default.AccountBox , contentDescription = "Notification  Icon")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Notification ")
            Spacer(modifier = Modifier.width(48.dp))
            Switch(checked = viewModel.notificationSwitch, onCheckedChange = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnNotificationSwitchChange(it))
            })

        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("On Days", color = Color.Red)

        Row{
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.MONDAY))
                if(viewModel.onDays[DayOfWeek.MONDAY.ordinal]=='M'){
                    mondayColor=Color.Green
                }
                else{
                    mondayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  mondayColor, backgroundColor = mondayColor)
            ) {
                Text(text = "M", color = Color.White)

            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.TUESDAY))
                if(viewModel.onDays[DayOfWeek.TUESDAY.ordinal]=='T'){
                    tuesdayColor=Color.Green
                }
                else{
                    tuesdayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  tuesdayColor, backgroundColor = tuesdayColor)
            ) {
                Text(text = "T", color = Color.White)

            }

            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.WEDNESDAY))
                if(viewModel.onDays[DayOfWeek.WEDNESDAY.ordinal]=='W'){
                    wednesDayColor=Color.Green
                }
                else{
                    wednesDayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  wednesDayColor, backgroundColor = wednesDayColor)
            ) {
                Text(text = "W", color = Color.White)

            }

            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.THURSDAY))
                if(viewModel.onDays[DayOfWeek.THURSDAY.ordinal]=='H'){
                    thrusdayColor=Color.Green
                }
                else{
                    thrusdayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  thrusdayColor, backgroundColor = thrusdayColor)
            ) {
                Text(text = "Th", color = Color.White)

            }

            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.FRIDAY))
                if(viewModel.onDays[DayOfWeek.FRIDAY.ordinal]=='F'){
                    fridayColor=Color.Green
                }
                else{
                    fridayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  fridayColor, backgroundColor = fridayColor)
            ) {
                Text(text = "F", color = Color.White)

            }

            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.SATURDAY))
                if(viewModel.onDays[DayOfWeek.SATURDAY.ordinal]=='S'){
                    saturdayColor=Color.Green
                }
                else{
                    saturdayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  saturdayColor, backgroundColor = saturdayColor)
            ) {
                Text(text = "S", color = Color.White)

            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnDaysClick(DayOfWeek.SUNDAY))
                if(viewModel.onDays[DayOfWeek.SUNDAY.ordinal]=='U'){
                    sundayColor=Color.Green
                }
                else{
                    sundayColor=Color.Gray

                }


            },
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border= BorderStroke(2.dp, Color(0XFF0F9D58)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  sundayColor, backgroundColor = sundayColor)
            ) {
                Text(text = "Su", color = Color.White)

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Choose Time Interval", color = Color.Red)

        Row{
            Button(onClick = { timeStartDialogState.show() } , modifier = Modifier.padding(8.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(text = "Start time: ${viewModel.timeStart} End time: ${viewModel.timeEnd}")

        }


        MaterialDialog(
            dialogState =timeStartDialogState,
            buttons = {
                positiveButton(text = "Ok"){
                    timeEndDialogState.show()

                }
                negativeButton(text = "Cancel")
            }

        ) {
            Text("Start Time")

            timepicker(
                initialTime = LocalTime.now(),
                title = "Pick a time"
            ){
                pickedTimeStart=it
            }

        }

        MaterialDialog(
            dialogState =timeEndDialogState,
            buttons = {
                positiveButton(text = "Ok"){

                    viewModel.onEvent(SpecificTimeIntervalEvent.OnAddTimeRangeClick(formattedTimeStart,formattedTimeEnd))

                }
                negativeButton(text = "Cancel")
            }

        ) {
            Text("End Time")
            timepicker(
                initialTime = LocalTime.now(),
                title = "Pick a time"
            ){
                pickedTimeEnd=it
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Show Motivational Text", color = Color.Red)

        TextField(value = viewModel.motiText, onValueChange = {

            viewModel.onEvent(SpecificTimeIntervalEvent.OnMotiTextChange(it))



        },
            placeholder = {
                Text(text = "")
            },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))


        if(viewModel.isUpdate){
            Button(onClick = {
                viewModel.onEvent(SpecificTimeIntervalEvent.OnUpdateClick)
            }) {
                Text(text = "Update")
            }
        }

        else{
            Row{

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    viewModel.onEvent(SpecificTimeIntervalEvent.OnSaveClick)
                },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Save")

                }
            }

        }


    }
}