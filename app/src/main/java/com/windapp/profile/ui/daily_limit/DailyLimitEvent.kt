package com.windapp.profile.ui.daily_limit

import java.time.DayOfWeek

sealed   class DailyLimitEvent {

     data class OnAppLaunchSwitchChange(val flag:Boolean):DailyLimitEvent()
     data class OnNotificationSwitchChange(val flag:Boolean):DailyLimitEvent()
     data class OnDaysClick(val day:DayOfWeek):DailyLimitEvent()
     data class OnTimeSpentChange(val time:Int):DailyLimitEvent()
     data class OnMotiTextChange(val text:String):DailyLimitEvent()
     object OnSaveAndAddMoreClick:DailyLimitEvent()
     object OnSaveClick:DailyLimitEvent()
     object OnUpdateClick:DailyLimitEvent()


}