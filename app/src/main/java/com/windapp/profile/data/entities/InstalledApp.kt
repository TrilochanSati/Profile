package com.windapp.profile.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
   data class InstalledApp (

    @PrimaryKey(autoGenerate = false)
    var packageName:String,
    var applicationName:String,
    var isSelected:Boolean=false
           )