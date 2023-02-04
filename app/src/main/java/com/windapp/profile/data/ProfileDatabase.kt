package com.windapp.profile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.windapp.profile.data.entities.InstalledApp
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.data.entities.UsageLimitDaily

@Database(
    entities = [UsageLimitDaily::class,InstalledApp::class, SpecificTimeIntervals::class],
    version = 1
)
  abstract  class ProfileDatabase:RoomDatabase() {
      abstract val dao:ProfileDao
}