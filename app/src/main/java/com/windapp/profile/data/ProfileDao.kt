package com.windapp.profile.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.windapp.profile.data.entities.InstalledApp
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.data.entities.UsageLimitDaily
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    //Usage Limit Daily

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsageLimitDaily(usageLimitDaily: UsageLimitDaily)

    @Query("DELETE  FROM usagelimitdaily WHERE id=:id")
    suspend fun deleteUsageLimitDailyById(id: Int)

    @Query("SELECT * FROM usagelimitdaily")
    fun getUsageLimitDaily(): Flow<List<UsageLimitDaily>>

    @Query("SELECT * FROM usagelimitdaily WHERE id=:id")
   suspend fun getUsageLimitDailyById(id:Int):UsageLimitDaily

    @Query("SELECT * FROM usagelimitdaily WHERE packageName=:packageName")
   suspend fun getUsageLimitDailyByPackageName(packageName:String):List<UsageLimitDaily>

    @Query("SELECT * FROM usagelimitdaily WHERE packageName=:packageName")
     fun getFlowUsageLimitDailyByPackageName(packageName:String):Flow<List<UsageLimitDaily>>

    @Query("UPDATE usagelimitdaily SET blockApp=:blockApp, blockNotification=:blockNotification,onDays=:onDays,timeLimit=:timeLimit,motiText=:motiText WHERE id=:id")
  suspend  fun updateUsageLimitDailyById(id:Int, blockApp:Boolean, blockNotification:Boolean, onDays:String, timeLimit:Int, motiText:String)


  // Specific Time Intervals
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSpecificTimeInterval(specificTimeIntervals: SpecificTimeIntervals)

    @Query("DELETE  FROM specifictimeintervals WHERE id=:id")
    suspend fun deleteSpecificTimeIntervalById(id: Int)

    @Query("SELECT * FROM specifictimeintervals")
    fun getAllUsageSpecificTimeInterval(): Flow<List<SpecificTimeIntervals>>

    @Query("SELECT * FROM specifictimeintervals WHERE id=:id")
    suspend fun getSpecificTimeIntervalById(id:Int):SpecificTimeIntervals

    @Query("SELECT * FROM specifictimeintervals WHERE packageName=:packageName")
  suspend  fun getSpecificTimeIntervalByPackageName(packageName:String):List<SpecificTimeIntervals>

    @Query("SELECT * FROM specifictimeintervals WHERE packageName=:packageName")
      fun getFlowSpecificTimeIntervalByPackageName(packageName:String):Flow<List<SpecificTimeIntervals>>


    @Query("UPDATE specifictimeintervals SET blockApp=:blockApp, blockNotification=:blockNotification,onDays=:onDays,timeStart=:timeStart,timeEnd=:timeEnd,motiText=:motiText WHERE id=:id")
    suspend  fun updateSpecificTimeIntervalById(id:Int, blockApp:Boolean, blockNotification:Boolean, onDays:String, timeStart:String,timeEnd:String, motiText:String)



    // Installed App

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insertApplicationName(installedApp: InstalledApp)

    @Query("SELECT * FROM installedapp WHERE applicationName=:name")
    suspend fun findApplicationName(name:String):List<InstalledApp>

    @Query(" SELECT EXISTS (SELECT * FROM installedapp WHERE applicationName=:name)")
    suspend fun applicationNameExits(name:String):Boolean

    @Query("UPDATE installedapp  SET isSelected=:flag  WHERE packageName=:packName ")
    suspend fun enableSelected(packName:String,flag:Boolean)

    @Query("SELECT isSelected FROM installedapp WHERE packageName=:packName")
    suspend  fun isSelected(packName: String):Boolean

    @Query("DELETE FROM installedapp WHERE applicationName=:name")
    suspend  fun deleteApplicationName(name:String)

    @Query("SELECT * FROM installedapp")
    fun getAllApplicationName(): Flow<List<InstalledApp>>
}