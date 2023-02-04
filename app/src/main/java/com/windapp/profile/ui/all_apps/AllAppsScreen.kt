package com.windapp.profile.ui.all_apps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.windapp.profile.util.UiEvent
import com.windapp.profile.util.UtilUsageTimeline
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllAppsScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: AllAppsViewModel= hiltViewModel(),

) {
    var allInstalledApp=viewModel.allInstalledApp.collectAsState(initial = emptyList())


    var packNameBottomSheet by remember {
        mutableStateOf("")
    }
    var usageTimeDailyList=viewModel.getUsageLimitDailyByPackageName(packNameBottomSheet).collectAsState(
        initial = emptyList()
    )

    var specificTimeIntervalList=viewModel.getFlowSpecificTimeIntervalByPackageName(packNameBottomSheet).collectAsState(
        initial = emptyList()
    )


    val sheetState= rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    val scope= rememberCoroutineScope()



    LaunchedEffect(key1 = true ){
        scope.launch {
            sheetState.hide()

        }
        viewModel.uiEvent.collect{  event->
            when(event){
                is UiEvent.Navigate ->{
                    onNavigate(event)
                }
                is UiEvent.ShowBottomSheet->{
                 packNameBottomSheet=   event.packageName
                }

                else -> Unit
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.TopCenter
            ){

          Row{

              Image(painter = rememberDrawablePainter( viewModel.getIcon(packNameBottomSheet)), contentDescription ="" , modifier = Modifier.size(32.dp))
              Spacer(modifier = Modifier.width(16.dp))
              Text(text = viewModel.getAppName(packNameBottomSheet))
          }
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(usageTimeDailyList.value){
                        schedule->
                        UsageLimitDailyItem(
                            id=schedule.id!!,
                            packageName=schedule.packageName,
                            daysActive = schedule.onDays,
                            appCondition =schedule.blockApp ,
                            notiCondition = schedule.blockNotification,
                            timeLimit = schedule.timeLimit,
                            onEvent = viewModel::onEvent,
                            scope = scope,
                            sheetState = sheetState
                        )

                    }
                    items(specificTimeIntervalList.value){
                            schedule->
                        SpecificTimeScheduleItem(
                            id=schedule.id!!,
                            packageName=schedule.packageName,
                            daysActive = schedule.onDays,
                            appCondition =schedule.blockApp ,
                            notiCondition = schedule.blockNotification,
                            startTime = schedule.timeStart,
                            endTime=schedule.timeEnd,
                            onEvent = viewModel::onEvent,
                            scope = scope,
                            sheetState = sheetState
                        )

                    }
                }
            }

    },
        sheetBackgroundColor = Color.Yellow,

    ) {
        Column() {
            Text("All Installed Apps")

            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(allInstalledApp.value){
                        app->
                    var icon= viewModel.getIcon(app.packageName)

                    AppItem(icon = icon, appName = app.applicationName, packageName = app.packageName, isLocked =app.isSelected,scope,sheetState , onEvent =viewModel::onEvent )


                }

            }




        }

    }



}