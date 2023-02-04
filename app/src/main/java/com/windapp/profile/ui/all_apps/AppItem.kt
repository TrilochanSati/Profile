package com.windapp.profile.ui.all_apps

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppItem(
    icon:Drawable,
    appName:String,
    packageName:String,
    isLocked:Boolean,
    scope:CoroutineScope,
    sheetState:ModalBottomSheetState,
    onEvent:(AllAppsEvent)->Unit,
    modifier: Modifier=Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = rememberDrawablePainter(drawable = icon),
            contentDescription ="",
            modifier = Modifier.clickable {
                onEvent(AllAppsEvent.OnAppIconClick(packageName))
                scope.launch {
                    if(sheetState.isVisible) {
                        sheetState.hide()
                    } else {
                        sheetState.show()                   }
                }
            }

        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = appName)
        Spacer(modifier = Modifier.width(16.dp))
      Checkbox(checked = isLocked, onCheckedChange ={
          scope.launch {
              sheetState.hide()

          }
          onEvent(AllAppsEvent.OnAppClick(packageName))


      } )
        Spacer(modifier = Modifier.width(16.dp))
    }

}