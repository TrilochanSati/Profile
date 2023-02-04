package com.windapp.profile.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsageLimitDaily(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val packageName:String,
    val blockApp:Boolean,
    val blockNotification:Boolean,
    val onDays:String,
    val timeLimit:Int,
    val motiText:String,
    val profileType:String="usage_limit_daily"

)
