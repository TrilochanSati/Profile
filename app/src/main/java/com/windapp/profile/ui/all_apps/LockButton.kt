package com.windapp.profile.ui.all_apps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LockButton(onClick:(Boolean)->Unit={}) {
    var buttonState: LockButtonState by remember {
        mutableStateOf(LockButtonState.UNLOCKED)
    }

    val shape = RoundedCornerShape(corner = CornerSize(12.dp))

    val buttonBackgroudColor: Color =
        if (buttonState == LockButtonState.LOCKED)
            Color.White
        else
            Color.Blue

    val iconAsset: ImageVector =
        if (buttonState == LockButtonState.LOCKED)
            Icons.Default.Check
        else
            Icons.Default.Add

    val iconTintColor: Color =
        if (buttonState == LockButtonState.LOCKED)
            Color.Blue
        else
            Color.White


    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 1.dp, color = Color.Blue, shape = shape)
            .background(color = buttonBackgroudColor)
            .size(width = 40.dp, height = 24.dp)
            .clickable(
                onClick = {
                    buttonState =
                        if (buttonState == LockButtonState.UNLOCKED) {
                            onClick.invoke(true)
                            LockButtonState.LOCKED
                        } else {
                            onClick.invoke(false)
                            LockButtonState.UNLOCKED

                        }

                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconAsset,
            tint = iconTintColor,
            modifier = Modifier.size(16.dp),
            contentDescription = ""
        )
    }

}

enum class LockButtonState{
    UNLOCKED,
    LOCKED
}