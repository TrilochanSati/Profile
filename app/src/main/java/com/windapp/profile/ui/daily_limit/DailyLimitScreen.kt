package com.windapp.profile.ui.daily_limit

import android.widget.EditText
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.windapp.profile.R
import com.windapp.profile.util.UiEvent

import java.time.DayOfWeek

@Composable
fun DailyLimitScreen(
    onPopBackStack:()->Unit,
    viewModel: DailyLimitViewModel= hiltViewModel()

) {

    val scaffoldState= rememberScaffoldState()
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
                viewModel.onEvent(DailyLimitEvent.OnAppLaunchSwitchChange(it))
            })

            Spacer(modifier = Modifier.height(16.dp))




        }

        Row {
            Icon(imageVector = Icons.Default.AccountBox , contentDescription = "Notification  Icon")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Notification ")
            Spacer(modifier = Modifier.width(48.dp))
            Switch(checked = viewModel.notificationSwitch, onCheckedChange = {
                viewModel.onEvent(DailyLimitEvent.OnNotificationSwitchChange(it))
            })

        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("On Days", color = Color.Red)

        Row{
            OutlinedButton(onClick = {
                                     viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.MONDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.TUESDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.WEDNESDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.THURSDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.FRIDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.SATURDAY))
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
                viewModel.onEvent(DailyLimitEvent.OnDaysClick(DayOfWeek.SUNDAY))
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

        Text("Once I Have Spent", color = Color.Red)

        TextField(value = viewModel.timeLimit.toString(), onValueChange = {
            if(it==""){
                viewModel.onEvent(DailyLimitEvent.OnTimeSpentChange(0))

            }
            else{
                viewModel.onEvent(DailyLimitEvent.OnTimeSpentChange(it.toInt()))
            }


        },
            placeholder = {
                Text(text = "Enter time in Minutes")
            },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Show Motivational Text", color = Color.Red)

        TextField(value = viewModel.motiText, onValueChange = {

                viewModel.onEvent(DailyLimitEvent.OnMotiTextChange(it))



        },
            placeholder = {
                Text(text = "")
            },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))


        if(viewModel.isUpdate){
            Button(onClick = {
                viewModel.onEvent(DailyLimitEvent.OnUpdateClick)
            }) {
                Text(text = "Update")
            }
        }

        else{
            Row{
                Button(onClick = {
                    viewModel.onEvent(DailyLimitEvent.OnSaveAndAddMoreClick)
                },
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "Save and Add More")
                }
                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    viewModel.onEvent(DailyLimitEvent.OnSaveClick)
                },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Save")

                }
            }

        }


    }
}