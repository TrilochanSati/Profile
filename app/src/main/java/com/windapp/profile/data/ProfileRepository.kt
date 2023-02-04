package com.windapp.profile.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.windapp.profile.data.entities.InstalledApp
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.data.entities.UsageLimitDaily
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    // UsageLimit

    suspend fun insertUsageLimitDaily(usageLimitDaily: UsageLimitDaily)

    suspend fun deleteUsageLimitDailyById(id: Int)

    fun getUsageLimitDaily(): Flow<List<UsageLimitDaily>>

    suspend fun getUsageLimitDailyByPackageName(packageName:String):List<UsageLimitDaily>

    fun getFlowUsageLimitDailyByPackageName(packageName:String):Flow<List<UsageLimitDaily>>

    suspend  fun updateUsageLimitDailyById(id:Int, blockApp:Boolean, blockNotification:Boolean, onDays:String, timeLimit:Int, motiText:String)

    suspend fun getUsageLimitDailyById(id:Int):UsageLimitDaily

    // Specific Time Intervals

    suspend fun insertSpecificTimeInterval(specificTimeIntervals: SpecificTimeIntervals)

    suspend fun deleteSpecificTimeIntervalById(id: Int)

    fun getAllUsageSpecificTimeInterval(): Flow<List<SpecificTimeIntervals>>

    suspend fun getSpecificTimeIntervalById(id:Int): SpecificTimeIntervals

    suspend  fun getSpecificTimeIntervalByPackageName(packageName:String):List<SpecificTimeIntervals>

    fun getFlowSpecificTimeIntervalByPackageName(packageName:String):Flow<List<SpecificTimeIntervals>>

    suspend  fun updateSpecificTimeIntervalById(id:Int, blockApp:Boolean, blockNotification:Boolean, onDays:String, timeStart:String,timeEnd:String, motiText:String)



    // Installed App

    suspend  fun insertApplicationName(installedApp: InstalledApp)

    suspend   fun findApplicationName(name:String):List<InstalledApp>

    suspend  fun applicationNameExits(name:String):Boolean

    suspend  fun enableSelected(packName:String,flag:Boolean)

    suspend   fun isSelected(packName: String):Boolean

    suspend  fun deleteApplicationName(name:String)

    fun getAllApplicationName(): Flow<List<InstalledApp>>
}