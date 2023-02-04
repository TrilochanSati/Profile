package com.windapp.profile.ui.all_apps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.windapp.profile.data.entities.UsageLimitDaily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsageLimitDailyItem(
    id:Int,
    packageName:String,
    daysActive:String,
    appCondition:Boolean,
    notiCondition:Boolean,
    timeLimit:Int,
    onEvent:(AllAppsEvent)->Unit,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState

    ) {
    Column() {
        Divider(
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()  //fill the max height
                .width(5.dp)
        )
        Text(text = "Days Active", modifier = Modifier.padding(8.dp))
        Text(text = daysActive)
        Text(text = "Block Condition", modifier = Modifier.padding(8.dp))

        Row{
            if(appCondition){
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription ="" )
            }
            if(notiCondition){
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
            }
            Text(text = "$timeLimit mins Daily", modifier = Modifier.padding(8.dp))

            Icon(imageVector = Icons.Default.Delete, contentDescription = "", modifier = Modifier.clickable {

                onEvent(AllAppsEvent.OnProfileDeleteClick(id,ProfileType.UsageLimeDaily))

            })

            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Update", modifier = Modifier.clickable {
                scope.launch {
                    sheetState.hide()

                }
                onEvent(AllAppsEvent.OnProfileEditClick(id,packageName,ProfileType.UsageLimeDaily))

            })
        }




    }
}