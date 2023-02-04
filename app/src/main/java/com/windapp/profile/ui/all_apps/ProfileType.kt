package com.windapp.profile.ui.all_apps

sealed class ProfileType{
    object UsageLimeDaily:ProfileType()
    object SpecificTimeInterval:ProfileType()
}
