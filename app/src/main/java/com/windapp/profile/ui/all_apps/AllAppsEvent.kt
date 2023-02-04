package com.windapp.profile.ui.all_apps

   sealed class AllAppsEvent {
       data class OnAppClick(val packageName:String):AllAppsEvent()
       data class OnAppIconClick(val packageName:String):AllAppsEvent()
       data class OnProfileDeleteClick(val id:Int,val profileType: ProfileType):AllAppsEvent()
       data class OnProfileEditClick(val id:Int,val packageName: String,val porfileType: ProfileType):AllAppsEvent()

}