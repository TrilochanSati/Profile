package com.windapp.profile.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpecificTimeIntervals (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val packageName:String,
    val blockApp:Boolean,
    val blockNotification:Boolean,
    val onDays:String,
    val timeStart:String,
    val timeEnd:String,
    val motiText:String,
    val profileType:String="specific_time_intervals"

)