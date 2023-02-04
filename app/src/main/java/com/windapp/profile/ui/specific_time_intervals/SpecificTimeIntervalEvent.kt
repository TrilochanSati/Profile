package com.windapp.profile.ui.specific_time_intervals

import java.time.DayOfWeek

sealed   class SpecificTimeIntervalEvent {

    data class OnAppLaunchSwitchChange(val flag:Boolean):SpecificTimeIntervalEvent()
    data class OnNotificationSwitchChange(val flag:Boolean):SpecificTimeIntervalEvent()
    data class OnDaysClick(val day: DayOfWeek):SpecificTimeIntervalEvent()
    data class OnAddTimeRangeClick(val timeStart:String,val timeEnd:String):SpecificTimeIntervalEvent()
    data class OnMotiTextChange(val text:String):SpecificTimeIntervalEvent()
    object OnSaveClick:SpecificTimeIntervalEvent()
    object OnUpdateClick:SpecificTimeIntervalEvent()


}