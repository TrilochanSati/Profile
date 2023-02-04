package com.windapp.profile

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProfileApp:Application() {

    object APP{
        lateinit var  context: Context

    }
    override fun onCreate() {
        super.onCreate()
        APP.context=this
    }
}